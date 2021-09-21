package cn.gyw.corejava;

import cn.gyw.corejava.util.SystemUtil;
import org.junit.Test;

/**
 * 系统工具类测试
 */
public class SystemUtilTest {
	
	@Test
	public void showSystemProperties() {
		SystemUtil.printProperties();
	}

    @Test
    public void getCpuNums() {
        System.out.println(SystemUtil.getCpuCnt());
    }

}
