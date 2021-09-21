package cn.gyw.corejava.io.bio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

//文本文件输出的快捷方式
//1.5 在PrintWriter中添加了一个辅助构造器，使得你不必再每次希望创建文本文件并向
//其中写入时，都去执行所有的装饰工作
public class FileOutputShortcut {
	static String file = "E:\\Test\\FileOutputShortcut.out";
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader("E:\\Test\\test.txt"));
		PrintWriter out = new PrintWriter(file);
		int lineCount = 1;
		String s;
		while((s = in.readLine()) != null){
			out.println(lineCount++ +": "+s);
		}
		out.close();

		System.out.println(BufferedInputFile.read(file));
	}
}
