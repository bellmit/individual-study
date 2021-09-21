package cn.gyw.corejava.javaagent;

/**
 * -javaagent:*.jar
 */
public class JavaagentTest {

	public static void main(String[] args) {
		System.out.println("main start");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("main end");
	}
}
