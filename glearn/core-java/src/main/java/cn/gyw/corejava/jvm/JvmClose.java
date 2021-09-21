package cn.gyw.corejava.jvm;

/**
 * JVM 关闭
 */
public class JvmClose {

    public static void main(String[] args) {

        // 关闭钩子
        // Runtime.getRuntime().addShutdownHook();

        System.exit(0);

        // 强制关闭JVM
        Runtime.getRuntime().halt(0);
    }
}
