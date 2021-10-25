package cn.gyw.corejava.jvm.servertmpcode;

import java.lang.reflect.Method;

/**
 * JavaClass 执行工具
 */
public class JavaClassExecuter {

    /**
     * 执行外部传入的代表一个Java类的byte 数组<br>
     * 将输入的byte数组中代表java.lang.System的CONSTANT_Utf8_info 常量修改为劫持后的HackSystem类
     * 执行方法为该类的static mail(String[] args) 方法，输出结果为该类向System.out/err 输出的信息
     * @param classByte 代表一个Java类的byte数组
     * @return 执行结果
     */
    public static String execute(byte[] classByte) {
        HackSystem.cleanBuffer();
        ClassModifier classModifier = new ClassModifier(classByte);
        byte[] modiBytes = classModifier.modifyUTF8Constant("java/lang/System",
                "com/gyw/jvm/server_tmp_code/HackSystem");
        HotSwapClassLoader loader = new HotSwapClassLoader();
        Class clazz = loader.loadByte(modiBytes);
        try {
            Method method = clazz.getMethod("mail", new Class[] {String[].class});
            method.invoke(clazz.newInstance());
        } catch (Throwable e) {
            e.printStackTrace(HackSystem.out);
        }
        return HackSystem.getBufferString();
    }
}
