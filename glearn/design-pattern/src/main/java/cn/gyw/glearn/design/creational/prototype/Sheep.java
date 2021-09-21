package cn.gyw.glearn.design.creational.prototype;

import java.io.*;
import java.util.Date;

/**
 * 原型模式：浅克隆/深克隆
 * Cloneable是一个空接口（标记接口），是一个规范。但是如果要克隆这个类对象的话必须实现Cloneable接口
 * Created by guanyw on 2018/7/9.
 */
public class Sheep implements Cloneable,Serializable {

	private String sname;
    private Date birthday;

    /**
     * 重写Object对象的clone方法
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        //直接调用Object对象的clone方法
        Object obj = super.clone();
        return obj;
    }

	public Sheep() {
	}

	public Sheep(String sname, Date birthday) {

		this.sname = sname;
		this.birthday = birthday;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public static void main(String[] args) throws Exception {
    	// 1.浅克隆
//		Date date = new Date();
//		Sheep s1 = new Sheep("原型羊1", date);
//		System.out.println(s1);
//		System.out.println("原型羊的时间：" + s1.getBirthday());
//		Thread.sleep(1000);
//		date.setTime(new Date().getTime());
//		Sheep s2 = (Sheep) s1.clone();
//		s2.setBirthday(date);
//		System.out.println("克隆羊的时间：" + s2.getBirthday());
//
//		System.out.println("此时原型羊的时间：" + s1.getBirthday());

		// 2. 通过clone() 方法添加引用

		// 3.通过序列化和烦序列化
		Date date = new Date();
		Sheep s1 = new Sheep("原型羊1", date);

		// 序列化
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(s1);
		byte[] bytes = baos.toByteArray();

		// 反序列化
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bais);
		Sheep s2 = (Sheep) ois.readObject();

		//原型羊的信息
        System.out.println(s1);
        System.out.println("原日期："+s1.getBirthday());
        date.setTime(34732834827389L);//改变原有date的值
        System.out.println("改变后的日期："+date.toString());
        //克隆羊的信息
        System.out.println("---------------------------------");
        System.out.println(s2);
        System.out.println(s2.getBirthday());
	}
}
