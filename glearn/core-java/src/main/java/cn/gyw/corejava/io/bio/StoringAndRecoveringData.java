package cn.gyw.corejava.io.bio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/* 存储和恢复数据
 * PrintWriter 可以对数据进行格式化，以便阅读。
 * 但是为了输出可供另一个"流"恢复的数据，我们需要使用DataOutputStream写入数据
 * 并用DataInputStream恢复数据。一一对应，保证读写。
 * 缺点：
 *     必须知道流中数据项所在的确切位置，要么为文件的数据采用固定的格式；要么将额外的信息保存到文件中，
 *     以便可以对其解析以确定数据的存放位置。
 * 注意：
 *     对象序列化和XML可能是更容易的存储和读取复杂数据结构的方式
*/
public class StoringAndRecoveringData {
	static String file = "E:\\Test\\Data.txt";
	public static void main(String[] args) throws Exception {
		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
		out.writeDouble(2.12156);
		out.writeUTF("that is pig");
		out.writeDouble(1.1345123);
		out.writeUTF("square toot of 2");
		out.close();

		DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
		System.out.println(in.readDouble());
		System.out.println(in.readUTF());
		System.out.println(in.readDouble());
		System.out.println(in.readUTF());
	}
}
