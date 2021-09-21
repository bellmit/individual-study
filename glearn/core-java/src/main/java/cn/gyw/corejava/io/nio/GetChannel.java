package cn.gyw.corejava.io.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/* 旧I/O类库三个类被修改：FileInputStream，FileOutputStream，RandomAccessFile
 * Reader和Writer这种字符模式类不能用于产生通道。
 * java.nio.channels.Channels类提供了实用方法，用以在通道中产生Reader和Writer。
 * */
public class GetChannel {
	private static final int BSIZE = 1024;
	public static void main(String[] args) throws Exception {
		//Write a file;
		FileChannel fc =
				new FileOutputStream("E:\\Test\\test-2.txt").getChannel();
		fc.write(ByteBuffer.wrap("Some text".getBytes()));
		fc.close();

		fc = new RandomAccessFile("E:\\Test\\test-2.txt","rw").getChannel();
		fc.position(fc.size());//Move to the end
		fc.write(ByteBuffer.wrap("Some more".getBytes()));
		fc.close();

		//Read the file;
		fc = new FileInputStream("E:\\Test\\test-2.txt").getChannel();
		ByteBuffer buff = ByteBuffer.allocate(BSIZE);//动态分配空间
		fc.read(buff);//告知FileChannel向ByteBuffer存储字节
		buff.flip();//做好让别人读取字节的准备
		while(buff.hasRemaining())
			System.out.print((char)buff.get());
	}
}
