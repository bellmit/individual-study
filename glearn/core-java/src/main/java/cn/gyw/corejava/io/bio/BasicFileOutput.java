package cn.gyw.corejava.io.bio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

//基本的文件输出
public class BasicFileOutput {
	static String file = "E:\\Test\\BasicFileOutput.out";
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader("E:\\Test\\test.txt"));
		//为了提供格式化机制，使用PrintWriter
		//按照这种方式创建的数据文件可以作为普通文本文件读取
		PrintWriter out = new PrintWriter(new FileWriter(file));
		int lineCount = 1;
		String s;
		while((s = in.readLine()) != null){
			out.println(lineCount++ + " : " + s);
		}

		out.close();

		System.out.println(BufferedInputFile.read(file));
	}
}
