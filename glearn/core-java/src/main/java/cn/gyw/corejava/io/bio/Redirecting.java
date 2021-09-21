package cn.gyw.corejava.io.bio;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/* 标准I/O重定向：
 * Java的System类提供了一些简单的静态方法调用，以允许我们对标准输入、输出和错误I/O流进行重定向：
 * setIn(InputStream),setOut(PrintStream),setErr(PrintStream).
 * */
public class Redirecting {
	public static void main(String[] args) throws Exception {
		PrintStream console = System.out;
		//标准输入附接到文件上
		BufferedInputStream in = new BufferedInputStream(new FileInputStream("E:\\Test2\\test.txt"));
		//标准输出附接到文件上
		PrintStream out = new PrintStream(
				new FileOutputStream("E:\\Test\\test.out"));
		//设置标准输入、输出重定向
		System.setIn(in);
		System.setOut(out);
		System.setErr(out);

		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in));
		String s;
		while((s = br.readLine())!=null)
			System.out.println(s);
		out.close();

		//恢复系统输出
		System.setOut(console);
	}
}
