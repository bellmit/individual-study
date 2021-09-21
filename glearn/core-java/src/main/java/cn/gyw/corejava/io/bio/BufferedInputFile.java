package cn.gyw.corejava.io.bio;

import java.io.BufferedReader;
import java.io.FileReader;

//缓冲输入文件
public class BufferedInputFile {

	//可以使用String或File文件作为文件名的FileInputReader
	public static String read(String fileName) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String s;
		StringBuilder sb = new StringBuilder();
		//readline返回null时，到达文件末尾
		while((s = in.readLine()) != null)
			sb.append(s + "\n");
		//去掉最后一个换行符
		sb.substring(0, sb.lastIndexOf("\n"));
		in.close();
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		System.out.println(read("E:\\Test\\test.txt"));
	}
}
