package cn.gyw.corejava.serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class SerializableTest {

	private String filePath = "D:\\Temp\\Serializable\\SourceObject";
	private SourceObject sourceObject;
	
	@Test
	public void writeObjectToFile() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(new File(filePath)));
			oos.writeObject(sourceObject);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void readObjectFromFile() {
		File file = new File(filePath);
        ObjectInputStream ois = null;
        try {
        	ois = new ObjectInputStream(new FileInputStream(file));
        	SourceObject obj = (SourceObject) ois.readObject();
        	System.out.println("read from file :" + obj);
        } catch (Exception e) {
        	e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Before
	public void initObject() {
		sourceObject = new SourceObject();
		sourceObject.setName("gyw");
		sourceObject.setAge(20);
		sourceObject.setGender("MALE");
		sourceObject.setBirthday(new Date());
		System.out.println("init object :" + sourceObject);
	}
}
