package cn.gyw.camel.message.convertor

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.codehaus.groovy.control.customizers.builder.CompilerCustomizationBuilder.withConfig

class ScriptMessageConvertor {

    private static final Logger log = LoggerFactory.getLogger(ScriptMessageConvertor.class)

    static final String SCRIPT_LOGGER = "_log"
    static final String MESSAGE = "_message"
    static final String OBJECT = "_object"
    static final String MESSAGE_TYPE = "_messageType"
    static final String OBJECT_TYPE = "_objectType"

    def identifyUnpackScript = "identify.unpack.groovy"
    def identifyPackScript = "identify.pack.groovy"
    def commonUnpackScript = "common.unpack.groovy"
    def commonPackScript = "common.pack.groovy"

    def loggerPrefix = "script"
    def scriptRoot = "classpath:."

    def scriptConfig = { config ->
        withConfig(config) {}
    }
    def customizeBinding = {}

    private GroovyScriptEngine scriptEngineInstance = null
    private GroovyScriptEngine delegatingScriptEngineInstance = null

    def unpack(message) {
        def messageType = identifyMessage(message, null)
        if (messageType) {
            return unpackMessage(messageType, message, null)
        }
        return null
    }

    def unpack(message, object) {
        def messageType = identifyMessage(message, object)
        if (messageType) {
            return unpackMessage(messageType, message, object)
        }
        return object
    }

    def pack(object) {
        def objectType = identifyObject(object)
        if (objectType) {
            return packMessage(objectType, object)
        }
        return null
    }

    protected identifyMessage(message, object) {
        def messageType = null
        Binding binding = new Binding()
        binding.setVariable(SCRIPT_LOGGER, LoggerFactory.getLogger("${loggerPrefix}.identify.unpack"))
        binding.setVariable(MESSAGE, message)
        binding.setVariable(OBJECT, object)
        customizeBinding.call(binding)
        try {
            def result = scriptEngine.run(identifyUnpackScript, binding)
            if (result) {
                messageType = result
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace()
            throw e
        }
        return messageType
    }

    protected unpackMessage(messageType, message, object) {
        Binding commonBinding = new Binding()
        commonBinding.setVariable(SCRIPT_LOGGER, LoggerFactory.getLogger("${loggerPrefix}.common.unpack"))
        commonBinding.setVariable(MESSAGE_TYPE, messageType)
        commonBinding.setVariable(MESSAGE, message)
        commonBinding.setVariable(OBJECT, object)
        customizeBinding.call(commonBinding)

        Binding binding = new Binding()
        binding.setVariable(MESSAGE_TYPE, messageType)
        binding.setVariable(MESSAGE, message)
        binding.setVariable(OBJECT, object)
        customizeBinding.call(binding)

        try {
            log.debug "Call {}.unpack.groovy", messageType
            binding.setVariable(SCRIPT_LOGGER, LoggerFactory.getLogger("${loggerPrefix}.${messageType}.unpack"))
            def commonScript = scriptEngine.createScript(commonUnpackScript, commonBinding)
            def unpackScriptObject = (DelegatingScript) delegatingScriptEngine.createScript("${messageType}.unpack.groovy", binding)
            unpackScriptObject.setDelegate(commonScript)
            def result = unpackScriptObject.run()

            return result
        } catch (ResourceException e) {
            e.printStackTrace()
        } catch (Exception e) {
            e.printStackTrace()
        }
        return null
    }

    protected identifyObject(object) {
        def objectType = null
        Binding binding = new Binding()
        binding.setVariable(OBJECT, object)
        customizeBinding.call(binding)
        try {
            def result = scriptEngine.run(identifyPackScript, binding)
            if (result) {
                objectType = result
            } else {
            }
        } catch (Exception e) {
            throw e
        }
        return objectType
    }

    protected packMessage(objectType, object) {
        Binding commonBinding = new Binding()
        commonBinding.setVariable(OBJECT_TYPE, objectType)
        commonBinding.setVariable(OBJECT, object)
        customizeBinding.call(commonBinding)

        Binding binding = new Binding()
        binding.setVariable(OBJECT_TYPE, objectType)
        binding.setVariable(OBJECT, object)
        customizeBinding.call(binding)

        try {
            log.debug "Calling {}.pack.groovy", objectType
            binding.setVariable(SCRIPT_LOGGER, LoggerFactory.getLogger("${loggerPrefix}.${objectType}.pack"))

            def commonScript = scriptEngine.createScript(commonPackScript, commonBinding)
            def packScriptObject = (DelegatingScript) delegatingScriptEngine.createScript("${objectType}.pack.groovy", binding)
            packScriptObject.setDelegate(commonScript)
            def result = packScriptObject.run()
            return result
        } catch (ResourceException e) {
            e.printStackTrace()
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    protected GroovyScriptEngine getScriptEngine() {
        synchronized (ScriptMessageConvertor.class) {
            if (!scriptEngineInstance) {
                scriptEngineInstance = new GroovyScriptEngine(normalizeScriptRoot());
                scriptConfig(scriptEngineInstance.config)
            }
        }

        return scriptEngineInstance
    }

    protected GroovyScriptEngine getDelegatingScriptEngine() {
        synchronized (ScriptMessageConvertor.class) {
            if (!delegatingScriptEngineInstance) {
                delegatingScriptEngineInstance = new GroovyScriptEngine(normalizeScriptRoot());
                scriptConfig(delegatingScriptEngineInstance.config)
                delegatingScriptEngineInstance.config.setScriptBaseClass(DelegatingScript.class.getName())
            }
        }
        return delegatingScriptEngineInstance
    }

    private normalizeScriptRoot() {
        if (scriptRoot instanceof String)
            return scriptRoot.toURL();
        else if (scriptRoot instanceof URI)
            return ((URI) scriptRoot).toURL()
        else
            return scriptRoot
    }

}
