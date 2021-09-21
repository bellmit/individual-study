package cn.gyw.thirdpart.xml;

import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class Dom4jUtil {

	public Document createXmlDocument(){
		Document document = DocumentHelper.createDocument();

		Element root = document.addElement("result");
		Element subElement1 = root.addElement("code");
		subElement1.addText("111");
		Element subElement2 = root.addElement("data");

		Element person1 = subElement2.addElement("person");
		person1.addElement("name").setText("网二");
		person1.addElement("age").addCDATA("<性别保密>");
		person1.addElement("url").setText("123@qq.com");

		Element courses = person1.addElement("courses");
		Element course1 = courses.addElement("course");
		course1.addElement("courseName").setText("语文");
		course1.addElement("courseMarks").setText("90");

		Element course2 = courses.addElement("course");
		course2.addElement("courseName").setText("数学");
		course2.addElement("courseMarks").setText("90");

		Element course3 = courses.addElement("course");
		course3.addElement("courseName").setText("英语");
		course3.addElement("courseMarks").setText("90");

		Element person2 = subElement2.addElement("person");
		person2.addElement("course").addAttribute("courseName", "地理");

		return document;
	}

	public Document parseText(String xmlString) {
		Document document = null;
		try {
			document = DocumentHelper.parseText(xmlString);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}

	public String parseDocument(Document document) {
		String xmlString = document.asXML();

		return xmlString;
	}

	//写入文件
	public void fileWriter(String filePath){
		FileWriter out = null;
        try {
            out = new FileWriter(filePath);  //写入文件
            createXmlDocument().write( out );

            OutputFormat format = OutputFormat.createPrettyPrint();  //转换成字符串
            format.setEncoding("UTF-8");
            XMLWriter writer = new XMLWriter( System.out, format );
            writer.write( createXmlDocument() );
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if (out!=null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}

}
