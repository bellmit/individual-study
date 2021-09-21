package cn.gyw.platform.tools.logging;

import java.io.InputStream;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

public class YamlTest {

	@Test
	public void load() {
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.yml");
		
		Yaml yaml = new Yaml();
		Object result = yaml.load(in);
		System.out.println(result);
	}
	
}
