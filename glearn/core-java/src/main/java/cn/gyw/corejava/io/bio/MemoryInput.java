package cn.gyw.corejava.io.bio;

import java.io.StringReader;

//从内存中输入
public class MemoryInput {
	public static void main(String[] args) throws Exception {
		StringReader in = new StringReader(BufferedInputFile.read("MemoryInput.java"));
		int c;
		//read是以int返回下一个字节
		while((c = in.read()) != -1)
			System.out.println((char)c);
	}
}
