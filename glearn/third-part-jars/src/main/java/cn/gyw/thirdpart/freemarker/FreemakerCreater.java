package cn.gyw.thirdpart.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.time.FastDateFormat;

import java.io.*;
import java.util.*;

/**
 * Freemarker 模板文件生成工具类
 * Created by guanyw on 2018/9/29.
 */
public class FreemakerCreater {

	private static final FastDateFormat dateTimeFormat = FastDateFormat.getInstance("yyyyMMddHHmmssSSS");

	public static void main(String[] args) {
		Writer writer = null;
		try {
			// 1.创建Configuration对象
			//创建一个合适的Configration对象
			Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
			// 通过文件目录找到模板
	//		configuration.setDirectoryForTemplateLoading(
	//				new File("D:\\project\\webProject\\WebContent\\WEB-INF\\template"));
			configuration.setClassForTemplateLoading(FreemakerCreater.class, "/freemarker");
			// 模板编码
			configuration.setDefaultEncoding("UTF-8");
			// 模板异常处理
			configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
//			configuration.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);
			// 不记录异常
			configuration.setLogTemplateExceptions(false);

			// 获取模板
			Template template = configuration.getTemplate("template_demo.ftl");
			String outFilePath = "E:\\13_Test\\freemarker\\freemarker"
//					+ dateTimeFormat.format(new Date())
					+ "_demo"
					+ ".html";

			// 获取数据
			Map<String, Object> datas = createDataMap();

			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFilePath), "UTF-8"));
			template.process(datas, writer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("文档创建成功...");
	}

	private static Map<String, Object> createDataMap() {
		Map<String, Object> datas = new HashMap<>();
		List<String> list = new ArrayList<>(4);
		list.add("AAAA");
		list.add("CCC");
		list.add("DDD");
		list.add("EEEEE");
		datas.put("nameList", list);
		datas.put("date", new Date());
		datas.put("boolean", new Boolean(true));
		return datas;
	}
}
