package cn.gyw.platform.common.web;

import org.junit.Test;

public class TempTest {

	@Test
	public void stringReplace() {
		String key = "keywordaaaa";
		System.out.println(key.replaceFirst("^keyword", ""));
		System.out.println("aakeywordName".replaceFirst("^keyword", ""));
		System.out.println("keyworName".replaceFirst("^keyword", ""));
		System.out.println(" keywordName".replaceFirst("^keyword", ""));
		System.out.println("keywordName".replaceFirst("^keyword", ""));
	}
}
