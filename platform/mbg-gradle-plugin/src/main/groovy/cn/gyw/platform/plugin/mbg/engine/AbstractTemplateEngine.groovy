package cn.gyw.platform.plugin.mbg.engine

import cn.gyw.platform.plugin.mbg.config.ConfigBuilder
import cn.gyw.platform.plugin.mbg.config.OutputFile

import java.nio.file.Paths

abstract class AbstractTemplateEngine {

	/**
	 * 配置信息
	 */
	ConfigBuilder configBuilder

	/**
	 * <p>
	 * 模板引擎初始化
	 * </p>
	 */
	AbstractTemplateEngine init(ConfigBuilder configBuilder) {
		this.configBuilder = configBuilder
		configBuilder.pathInfo[OutputFile.DTO] = configBuilder.entityDtoPackage.replace(".", "/")
		configBuilder.pathInfo[OutputFile.SERVICE]=configBuilder.servicePackage.replace(".", "/")
		configBuilder.pathInfo[OutputFile.CONTROLLER]=configBuilder.controllerPackage.replace(".", "/")
		return this
	}

	/**
	 * 生成文件
	 */
	void batchOutput() {
		configBuilder.moduleList.each { module ->
			Map<String, Object> objectMap = this.getObjectMap(configBuilder, module)
			// *Dto.java
			outputDto(objectMap)
			// *Service.java
			outputService(objectMap)
			// *Controller.java
			outputController(objectMap)
		}
	}
	
	void outputDto(Map<String, Object> objectMap) {
		// MpDto.java
		String dtoPath = getPathInfo(OutputFile.DTO)
		if (!dtoPath.endsWith("/")) {
			dtoPath += "/"
		}
		String dtoFile = getBasePath() + String.format(dtoPath + "%s.java", objectMap.get("entityDtoName"))
		if (checkOverwrite(dtoFile)) {
			writer(objectMap, "dto.java.ftl", dtoFile)	
		}
	}

	void outputService(Map<String, Object> objectMap) {
		// MpService.java
		String servicePath = getPathInfo(OutputFile.SERVICE)
		if (!servicePath.endsWith("/")) {
			servicePath += "/"
		}
		String serviceFile = getBasePath() + String.format(servicePath + "%s.java", objectMap.get("serviceName"))
		if (checkOverwrite(serviceFile)) {
			writer(objectMap, "service.java.ftl", serviceFile)
		}
	}

	void outputController(Map<String, Object> objectMap) {
		// MpController.java
		String controllerPath = getPathInfo(OutputFile.CONTROLLER)
		if (!controllerPath.endsWith("/")) {
			controllerPath += "/"
		}
		String controllerFile = getBasePath() + String.format(controllerPath + "%s.java", objectMap.get("controllerName"))
		if (checkOverwrite(controllerFile)) {
			writer(objectMap, "controller.java.ftl", controllerFile)
		}
	}

	def getObjectMap(ConfigBuilder config, String module) {
		Map<String, Object> objectMap = [:]
		def moduleLowerCamel = module[0].toLowerCase() + module[1..-1]
		def mapperName = String.format("%sMapper", module)
		def superControllerName =
				configBuilder.superControllerClass.substring(configBuilder.superControllerClass.lastIndexOf(".") + 1)
		def superServiceName =
				configBuilder.superServiceClass.substring(configBuilder.superServiceClass.lastIndexOf(".") + 1)

		objectMap.put("restControllerStyle", configBuilder.restControllerStyle)
		objectMap.put("entityClassPackage", configBuilder.entityPackage + "." + module)
		objectMap.put("entityDtoPackage", configBuilder.entityDtoPackage)
		objectMap.put("mapperClassPackage", configBuilder.xmlParams['xml.mapperPackage'] + "." + mapperName)
		objectMap.put("superServiceClassPackage", configBuilder.superServiceClass)
		objectMap.put("servicePackage", configBuilder.servicePackage)
		objectMap.put("superControllerClassPackage", configBuilder.superControllerClass)
		objectMap.put("controllerPackage", configBuilder.controllerPackage)
		objectMap.put("superControllerName", superControllerName)
		objectMap.put("superServiceName", superServiceName)
		objectMap.put("serviceName", String.format("%sService", module))
		objectMap.put("controllerName", String.format("%sController", module))
		objectMap.put("mapperName", mapperName)
		objectMap.put("entityName", module)
		objectMap.put("entityDtoName", String.format("%sDto", module))
		objectMap.put("moduleLower", moduleLowerCamel)
		objectMap.put("fieldServiceName", String.format("%sService", moduleLowerCamel))
		objectMap.put("fieldMapperName", String.format("%sMapper", moduleLowerCamel))
		return objectMap
	}
	
	private boolean checkOverwrite(String filePath) {
		return !Paths.get(filePath).toFile().exists() || configBuilder.overwrite
	}

	/**
	 * 获取路径信息
	 *
	 * @param outputFile 输出文件
	 * @return 路径信息
	 */
	protected String getPathInfo(OutputFile outputFile) {
		return configBuilder.pathInfo.get(outputFile)
	}

	/**
	 * <p>
	 * 将模板转化成为文件
	 * </p>
	 *
	 * @param objectMap    渲染对象 MAP 信息
	 * @param templatePath 模板文件
	 * @param outputFile   文件生成的目录
	 */
	abstract void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception;

	/**
	 * <p>
	 * 处理输出目录
	 * </p>
	 */
	AbstractTemplateEngine mkdirs() {
		def controllerDir = getBasePath() + configBuilder.pathInfo[OutputFile.CONTROLLER]
		def serviceDir = getBasePath() + configBuilder.pathInfo[OutputFile.SERVICE]
		def dtoDir = getBasePath() + configBuilder.pathInfo[OutputFile.DTO]
		Paths.get(dtoDir).toFile().mkdirs()
		Paths.get(serviceDir).toFile().mkdirs()
		Paths.get(controllerDir).toFile().mkdirs()
		println "mkdirs success:$controllerDir,$serviceDir,$dtoDir"
		return this;
	}
	
	// 获取项目源码真实路径
	private String getBasePath() {
		String srcPath = configBuilder.javaProjectDir.getAbsolutePath()
		if (!srcPath.endsWith("/")) {
			srcPath += "/"
		}
		return srcPath
	}
}
