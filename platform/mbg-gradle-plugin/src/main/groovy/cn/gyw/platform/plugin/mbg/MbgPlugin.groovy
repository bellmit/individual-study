package cn.gyw.platform.plugin.mbg

import org.gradle.api.Plugin
import org.gradle.api.Project

import cn.gyw.platform.plugin.mbg.extension.FtlExtension
import cn.gyw.platform.plugin.mbg.extension.MbgExtension
import cn.gyw.platform.plugin.mbg.extension.MbgJdbcExtension
import cn.gyw.platform.plugin.mbg.extension.MbgXmlExtension

class MbgPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		project.extensions.create("mbg", MbgExtension)
		project.mbg.extensions.create("jdbc", MbgJdbcExtension)
		project.mbg.extensions.create("xml", MbgXmlExtension)
		project.mbg.extensions.create("ftl", FtlExtension)

		project.task("mbg", type: MbgTask)
	}
}
