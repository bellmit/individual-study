package cn.gyw.corejava.io.bio;

import java.io.RandomAccessFile;

/* 读取随机访问文件
 * 使用RandomAccessFile ，必须知道文件排版，才能正确操作它。
 *
 * */
public class UsingRandomAccessFile {
	static String file ="E:\\Test\\rtest.dat";
	static void display() throws Exception {
		RandomAccessFile rf = new RandomAccessFile(file, "r");
		for(int i=0;i<7;i++)
			System.out.println("value " + i + ":" + rf.readDouble());
		System.out.println(rf.readUTF());
		rf.close();
	}

	public static void main(String[] args) throws Exception {
		RandomAccessFile rf = new RandomAccessFile(file, "rw");
		for(int i=0;i<7;i++)
			rf.writeDouble(i*1.414);
		rf.writeUTF("the end of the file");
		rf.close();

		display();

		rf = new RandomAccessFile(file,"rw");
		rf.seek(5*8);
		rf.writeDouble(47.00001);
		rf.close();

		display();
	}
}
