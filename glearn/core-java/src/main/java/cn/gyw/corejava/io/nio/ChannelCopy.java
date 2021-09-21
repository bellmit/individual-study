package cn.gyw.corejava.io.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

//简单文件复制
//如果打算使用缓冲器执行进一步的read操作，必须调用clear来为每个read做好准备
public class ChannelCopy {
	private static final int BSIZE = 1024;
	public static void main(String[] args) throws Exception {
		if(args.length != 2){
			System.out.println("arguments : sourcefile destfile ");
			System.exit(1);
		}
		FileChannel
			in = new FileInputStream(args[0]).getChannel(),
			out = new FileOutputStream(args[1]).getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
		/* 每次read操作后，数据输入到缓冲器中，flip则是准备缓冲器以便它的信息可以由write提取。
		 * write操作之后，信息仍在缓冲器中，接着clear操作则对所有的内部指针重新安排，以便
		 * 缓冲器在另一个read操作旗舰能够做好接受数据的准备。
		 * 理想方式：transferTo和transferFrom,允许我们将一个通道和另一个通道直接相连。
		 * */
		while(in.read(buffer) != -1){ //返回-1表示已达文件末尾
			buffer.flip(); //prepare for writing
			out.write(buffer);
			buffer.clear(); //prepare for reading
		}

	}
}
