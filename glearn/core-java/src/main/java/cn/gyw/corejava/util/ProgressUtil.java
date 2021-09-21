package cn.gyw.corejava.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.util.regex.Matcher;

/**
 * java Runtime Util
 */
public final class ProgressUtil {

	// -cp :其他class依赖；-sourcepath:其他java文件依赖；
	private static final String JAVA_PATH = "Path=C:\\java\\jdk1.8.0_171\\bin";
	private static final String JAVA_CLASSPATH = "CLASSPATH=C:\\java\\jdk1.8.0_171\\lib";

	private static final String OUT_DIR = "D:\\Temp\\code";

	private static final String MAIN_PATH = "src\\main\\java\\";
	private static final String TEST_PATH = "src\\test\\java\\";

	/**
	 * 运行 src/main/java 类
	 */
	public static void runMain(String className, String runParams) {
		run(className, MAIN_PATH, runParams);
	}

	/**
	 * 运行 src/test/java 类
	 */
	public static void runTest(String className, String runParams) {
		run(className, TEST_PATH, runParams);
	}

	/**
	 * 
	 * @param className 全类名
	 */
	private static void run(String className, String distinct, String runParams) {
		String workDir = System.getProperty("user.dir");
		String fileSeparator = Matcher.quoteReplacement(System.getProperty("file.separator"));
		StringBuilder builder = new StringBuilder();
		builder.append("cmd /c javac -encoding utf-8 -d ").append(OUT_DIR).append(" ").append(workDir)
				.append(System.getProperty("file.separator")).append(distinct)
				.append(className.replaceAll("\\.", fileSeparator)).append(".java") // 源文件
				.append(" && java -classpath ").append(OUT_DIR)
				.append(" ").append(runParams).append(" ") // 运行参数
				.append(className);
		System.out.println(builder.toString());
		String[] params = { JAVA_CLASSPATH, JAVA_PATH };

		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec(builder.toString(), params);
			// SequenceInputStream是一个串联流，能够把两个流结合起来，通过该对象就可以将
			// getInputStream方法和getErrorStream方法获取到的流一起进行查看了，当然也可以单独操作
			SequenceInputStream sis = new SequenceInputStream(process.getInputStream(), process.getErrorStream());
			InputStreamReader inst = new InputStreamReader(sis, "UTF-8");// 设置编码格式并转换为输入流
			BufferedReader br = new BufferedReader(inst);// 输入流缓冲区

			String res = null;
			StringBuilder sb = new StringBuilder();
			while ((res = br.readLine()) != null) {// 循环读取缓冲区中的数据
				sb.append(res + "\n");
			}
			br.close();
			process.waitFor();
			process.destroy();
			System.out.print(sb);// 输出获取的数据
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private ProgressUtil() {
	}
}
