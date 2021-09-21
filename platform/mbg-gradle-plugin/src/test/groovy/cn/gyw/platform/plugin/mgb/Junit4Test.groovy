package cn.gyw.platform.plugin.mgb

import org.junit.Test

class Junit4Test {
	
	@Test
	void stringOperation() {
		def module = "Admin"
		println module[0].toLowerCase() + module[1..-1]
	}
}
