package cn.gyw.platform.plugin.mbg.engine

import cn.gyw.platform.plugin.mbg.config.ConfigBuilder
import freemarker.template.Configuration
import freemarker.template.Template

class FreemarkerTemplateEngine extends AbstractTemplateEngine {

	Configuration configuration
	
	FreemarkerTemplateEngine init(ConfigBuilder configBuilder) {
		super.init(configBuilder)
		configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		configuration.setDefaultEncoding("UTF-8")
		configuration.setClassForTemplateLoading(FreemarkerTemplateEngine.class, "/templates")
		return this
	}
	
	@Override
	void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception {
		Template template = configuration.getTemplate(templatePath)
        FileOutputStream fileOutputStream = new FileOutputStream(new File(outputFile))
        template.process(objectMap, new OutputStreamWriter(fileOutputStream, "UTF-8"))
        fileOutputStream.close();
        println ("模板:" + templatePath + ";  文件:" + outputFile)
	}
	
}
