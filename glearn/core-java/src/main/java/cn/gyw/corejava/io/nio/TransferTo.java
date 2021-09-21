package cn.gyw.corejava.io.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class TransferTo {
	public static void main(String[] args) throws Exception {
		String sourceFile = "E:\\Test\\test-2.txt";
		String destFile = "E:\\Test\\test-4.txt";

		FileChannel
			in = new FileInputStream(sourceFile).getChannel(),
			out = new FileOutputStream(destFile).getChannel();
		in.transferTo(0, in.size(), out);

		//or:
//		out.transferFrom(in, 0, in.size());
	}
}
