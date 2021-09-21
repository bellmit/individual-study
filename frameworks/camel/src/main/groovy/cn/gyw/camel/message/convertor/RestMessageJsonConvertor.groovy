package cn.gyw.camel.message.convertor

import org.apache.camel.Exchange
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

import javax.annotation.PostConstruct

import static org.codehaus.groovy.control.customizers.builder.CompilerCustomizationBuilder.withConfig

@Service
class RestMessageJsonConvertor {

    private static final Logger log = LoggerFactory.getLogger(ScriptMessageConvertor.class)

    @Value('${rest.json.script.rootPath}')
    Resource scriptRoot

    def scriptConfig = { config ->
        withConfig(config) {}
    }

    ScriptMessageConvertor convertor

    @Autowired
    private Environment env

    @PostConstruct
    def initializeConvertor() {
        def customizeBinding = { binding ->
            binding.setVariable('_getProperty', { env.getProperty(it) })
        }

        convertor = new ScriptMessageConvertor(
            scriptRoot: scriptRoot.getURL(),
            scriptConfig: scriptConfig,
            customizeBinding: customizeBinding)
    }

	def unpack(Exchange exchange) {
		def request = ['header': exchange.in.headers, 'body': exchange.in.body]
		def object = convertor.unpack(request)
		exchange.in.body = object
	}

	def pack(Exchange exchange) {
		def object = ['header': exchange.in.headers, 'body': exchange.in.body]
		def message = convertor.pack(object)
		exchange.in.body = message
	}
}
