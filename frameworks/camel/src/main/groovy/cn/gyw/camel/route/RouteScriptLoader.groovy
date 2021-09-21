package cn.gyw.camel.route

import groovy.io.FileType
import org.apache.camel.CamelContext
import org.codehaus.groovy.control.CompilerConfiguration
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import javax.annotation.PostConstruct
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardWatchEventKinds
import java.nio.file.WatchEvent
import java.nio.file.WatchKey
import java.nio.file.WatchService
import java.util.concurrent.CountDownLatch

/**
 * 脚本加载器
 */
@Service
class RouteScriptLoader {

    static final Logger logger = LoggerFactory.getLogger(RouteScriptLoader.class)

    @Autowired
    CamelContext camelContext

    @Value('${route.script.rootPath}')
    String routePath

//    def configurationPathUri = ""
    def routePathUri
    // 路由集合map
    def scriptRoutes = [:]

    @PostConstruct
    def start() {
        def normalizePath = { path ->
            def pathUri
            if (path.startsWith('classpath:')) {
                pathUri = ClassLoader.getSystemResource(path - 'classpath:').toURI()
            } else {
                pathUri = Paths.get(path).toUri()
            }
            return pathUri
        }
        routePathUri = normalizePath(routePath)

        // delay start route to wait spring initialize everything
        Thread.start {
            Thread.sleep(5000)
            loadAllRoutes()
            startAllRoutes()
            // 文件监听，存在问题，待分析
            startWatcher()
        }
    }

    def loadAllRoutes() {
        new File(routePathUri).traverse(type: FileType.FILES, nameFilter: ~/.*\.route/) { routeFile -> loadRoutes(routeFile) }
    }

    def startAllRoutes() {
        def routes = camelContext.routes
        logger.info('{} routes loaded to camel context.', routes.size())

        routes.each { route ->
            def status = camelContext.getRouteStatus(route.id)
            logger.debug('route {} status = {}', route.id, status)
            if (!status.started) {
                logger.warn('route {} was loaded, but not started yet, current status = {}', route.id, status)
            }
        }

        logger.info('start all routes.')
        camelContext.startAllRoutes()
    }

    def startWatcher() {
        WatchService watcher = FileSystems.getDefault().newWatchService()

        Path scriptRoot = Paths.get(routePathUri)
        try {
            scriptRoot.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY)
        } catch (IOException e) {
            logger.error('start route script watcher failed: ', e)
        }

        def watcherThread = {
            logger.info('route script watcher thread started.')
            for (; ;) {
                WatchKey key
                try {
                    key = watcher.take()
                } catch (InterruptedException e) {
                    logger.error('route script watcher thread stopped: {}', e)
                    return
                }

                //Poll all the events queued for the key
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind()
                    if (kind == StandardWatchEventKinds.OVERFLOW)
                        continue

                    WatchEvent<Path> ev = (WatchEvent<Path>) event
                    Path changedFile = ev.context()

                    def action = kind.name() - 'ENTRY_'
                    logger.debug('monitored file change {} => {}', changedFile, action)

                    def actualFile = Paths.get(routePathUri).resolve(changedFile).toFile()
                    logger.debug('file changed: {}', actualFile.absolutePath);

                    // for DELETE, as file is deleted already, we could not know if it was really a
                    // script file or not, try to remove the routes anyway.
                    if (action != 'DELETE' && !actualFile.isFile())
                        continue

                    if (!actualFile.name.endsWith('.route'))
                        continue

                    switch (action) {
                        case 'CREATE':
                            // as observed, the newly copied file always create two events CREATE and MODIFY in
                            // sequence, ignore the file create at the moment.
                            // loadRoutes(fileChanged)
                            break
                        case 'DELETE':
                            removeRoutes(actualFile)
                            break
                        case 'MODIFY':
                            removeRoutes(actualFile)
                            loadRoutes(actualFile)
                            break
                    }
                }

                //reset is invoked to put the key back to ready state
                boolean valid = key.reset()
                //If the key is invalid, just exit.
                if (!valid) {
                    logger.error('watch key is not valid anymore!')
                    break
                }
            }

            logger.error('route script watcher thread stopped.')
        }

        Thread.start(watcherThread)
    }

    def relativePath(def file) {

        Path rootPath = Paths.get(routePathUri)

        if (file instanceof File) {
            return rootPath.relativize(file.toPath())
        } else if (file instanceof Path) {
            return rootPath.relativize(file)
        } else { // convert to string and try
            return rootPath.relativize(Paths.get(file.toString()))
        }
    }

    def loadRoutes(routeFile) {
        def compilerConfiguration = new CompilerConfiguration()
        compilerConfiguration.scriptBaseClass = DelegatingScript.class.name
        def gsh = new GroovyShell(new Binding(), compilerConfiguration)

        def routesAdded = []

        def scriptName = relativePath(routeFile)
        logger.debug("loading route {} - {}", scriptName, routeFile.absolutePath)

        try {
            camelContext.addRoutes(new RouteBase([script: routeFile.text, scriptName: scriptName, gsh: gsh, routesAdded: routesAdded]))
        } catch (Exception e) {
            logger.error('load route {} failed with exception:{}', scriptName, e)
        }

        scriptRoutes[scriptName] = routesAdded
    }

    def removeRoutes(routeFile) {
        def scriptName = relativePath(routeFile)
        logger.debug('removing routes for script: {}', scriptName)

        def routesInScript = scriptRoutes[scriptName]
        if (routesInScript) {
            def latch = new CountDownLatch(routesInScript.size())
            def stopRoute = { String routeId ->
                try {
                    camelContext.stopRoute(routeId)
                    logger.debug('route {} stopped.', routeId)
                } catch (Exception e) {
                    logger.warn('route {} stopping with exception: {}', routeId, e)
                } finally {
                    latch.countDown()
                }
            }

            routesInScript.each { routeId ->
                Thread.start stopRoute(routeId)
            }

            def allStopped = latch.await(10, TimeUnit.SECONDS)

            if (!allStopped) {
                logger.warn('not all routes defined in {} are stopped in 10 seconds', scriptName)
            }
            // clear the routes linked to the script
            scriptRoutes[scriptName] = []
        }
    }
}
