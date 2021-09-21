package cn.gyw.corejava.jvm;

import org.junit.Test;

import cn.gyw.corejava.util.ProgressUtil;

/**
 * jvm test
 */
public class JvmTest {
	
	/**
     * java.lang.System 由 Bootstrap 类加载器加载，打印为null
     *
     * 证明：Bootstrap不是类加载的，而是由JVM启动加载
     */
    @Test
    public void systemBootstrapLoader() {
        System.out.println(System.class.getClassLoader());
    }

	@Test
	public void progressUtilTest() {
		String className = "cn.gyw.corejava.jvm.JvmOptionBase";
		ProgressUtil.runTest(className, "-Xms10m -Xmx10m");
	}
}
