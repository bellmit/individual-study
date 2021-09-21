package cn.gyw.corejava.util;

/**
 * 系统工具类
 */
public class SystemUtil {
	
	public static void printProperties() {
		System.getProperties().entrySet().stream().forEach(entry -> {
			System.out.println(entry.getKey() + " >> " + entry.getValue());
		});
	}

    /**
     * 获取CPU 数
     * @return
     */
    public static int getCpuCnt() {
        return Runtime.getRuntime().availableProcessors();
    }

    private SystemUtil() {}
}
