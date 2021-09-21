package cn.gyw.platform.tools.logging;

import org.junit.Before;
import org.junit.Test;

import cn.gyw.platform.tools.logging.model.RemoteConfig;
import cn.gyw.platform.tools.logging.support.ConfigLoader;

public class GanymedRemoteServiceTest {

	private RemoteConfig config;

	@Test
	public void upload() {
	}

	@Test
	public void download() {
	}

	@Before
	public void init() {
		config = ConfigLoader.INSTANCE.getRemoteConfig();
	}
}
