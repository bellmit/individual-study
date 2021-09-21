package cn.gyw.corejava.string;

import org.junit.Test;

/**
 * java.lang.String 相关操作测试
 */
public class StringTest {

    private static String str = "";
    
    @Test
    public void testFormatMethod() {
    	System.out.println("1111");
    	String str = "123 %s --name %s";
    	String ret = String.format(str, "%s", "miqi");
    	System.out.println(ret);
    }

    /**
     * 字符串方法修改不可变性
     *
     * @param args
     */
    public static void main(String[] args) {
        str = "hello";
        changeStr(str);
        // str 的引用还是当前作用域的
        System.out.println(str);
        
        new StringTest().testFormatMethod();
    }

    private static void changeStr(String str) {
        // 修改的引用值，仅限于当前作用域
        str = "welcome";
    }
}
