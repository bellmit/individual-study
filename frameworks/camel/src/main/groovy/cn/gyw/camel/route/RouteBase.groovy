package cn.gyw.camel.route

import org.apache.camel.builder.RouteBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class RouteBase extends RouteBuilder {

    static final Logger logger = LoggerFactory.getLogger(RouteBase.class)

    GroovyShell gsh
    def script
    def scriptName
    def routesAdded

    @Override
    void configure() throws Exception {
        logger.debug("create route from script {}", scriptName)

        def proxyMethod = { methodName, arguments ->
            return this.invokeMethod(methodName, arguments)
        }

        def interceptor = new RouteDefinitionInterceptor([proxyMethod:proxyMethod])

        def scriptObject = gsh.parse(script)
        scriptObject.setDelegate(interceptor)
        scriptObject.run()

        logger.debug ('routes added with script {} => {}', scriptName, routesAdded)
    }
}

class RouteDefinitionInterceptor {
    def proxyMethod

    def methodMissing(String methodName, arguments) {
        return proxyMethod(methodName, arguments)
    }
}
