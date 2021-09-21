package cn.gyw.corejava.io.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

//Create a very large file using mapping
public class LargeMappedFiles {
	static int length = 0x8FFFFFF;
	public static void main(String[] args) throws Exception {
		MappedByteBuffer out =
				new RandomAccessFile("E:\\Test\\test.dat","rw").getChannel() //获得文件上的通道
				.map(FileChannel.MapMode.READ_WRITE, 0 , length); //特殊类型的直接缓冲器
		for(int i=0;i<length;i++)
			out.put((byte)'x');
		System.out.println("finished writing");
		for(int i=length/2;i<length/2+6;i++)
			System.out.println((char)out.get(i));
	}
}
