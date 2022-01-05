package cn.gyw.corejava;

import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

public class WorkLogGenerateTest {

	@Test
	public void shouldBuild() throws URISyntaxException {
		assert WorkLogByKPTP.buildCurrentYearWorkLog("e:\\3_工作日志\\");
	}
}
