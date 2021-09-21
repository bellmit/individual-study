package cn.gyw.platform.tools.logging;

import org.junit.Test;

import cn.gyw.platform.tools.logging.support.ConfigLoader;

public class ConfigLoaderTest {

	@Test
	public void load() {
	}
	
	@Test
	public void getRemoteConfig() {
		ConfigLoader.INSTANCE.getRemoteConfig();
	}
}
