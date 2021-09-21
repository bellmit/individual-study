package cn.gyw.corejava;

import java.net.URISyntaxException;

import org.junit.Test;

public class WorkLogGenerateTest {

	@Test
	public void shouldBuild() throws URISyntaxException {
		assert WorkLogByKPTP.buildCurrentYearWorkLog("D:\\1_Documents\\工作总结\\");
	}
}
