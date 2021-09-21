package cn.gyw.corejava.io.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/*
	Buffer读写数据一般遵循以下四个步骤：
	1.写入数据到Buffer
	2.调用flip()方法
	3.从Buffer中读取数据
	4.调用clear()方法或者compact()方法
 */
public class UsingBuffers {

	ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

	public static void main(String[] args) {
		UsingBuffers usingBufferObject = new UsingBuffers();
		// usingBufferObject.nomalBufferUsed();
//		usingBufferObject.writeToBuffer();
		usingBufferObject.readFromBuffer();
	}

	/**
	 * 从 Buffer 中读数据
	 * 1.从Buffer读取数据到Channel
	 * 2.使用get()方法从Buffer中读取数据
	 */
	private void readFromBuffer() {
		byteBuffer.put((byte) 22);
		byteBuffer.put((byte) 33);
		byteBuffer.put((byte) 44);
		System.out.println(byteBuffer);
		// 将Buffer从写模式切换到读模式。调用flip()方法会将position设回0，并将limit设置成之前position的值
		byteBuffer.flip();
		System.out.println(byteBuffer);

	}

	/**
	 * 向 Buffer 中写数据
	 * 1.从Channel写到Buffer
	 * 2.通过Buffer的put()方法写到Buffer里
	 */
	private void writeToBuffer() {
		byteBuffer.put((byte) 100);
		System.out.println(byteBuffer);
	}

	/**
	 * 普通使用 Buffer 缓冲区
	 */
	private void nomalBufferUsed() {
		RandomAccessFile randomAccessFile = null;
		try {
			randomAccessFile = new RandomAccessFile("F:\\data\\nio-data.txt", "rw");

			FileChannel fileChannel = randomAccessFile.getChannel();

			// buffer 缓冲区的分配
			ByteBuffer buffer = ByteBuffer.allocate(1024);

			// read file into buffer
			int byteRead = fileChannel.read(buffer);
			while (byteRead != -1) {
				// 复位，读模式从头开始
				buffer.flip();
				while (buffer.hasRemaining()) {
					System.out.println(buffer.get());
				}
				// 清空缓冲区
				buffer.clear();
			}
			randomAccessFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
