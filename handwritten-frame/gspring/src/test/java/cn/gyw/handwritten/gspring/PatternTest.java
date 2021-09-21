package cn.gyw.handwritten.gspring;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PatternTest {

	@Test
	public void aopService() {
		String str = "public .* cn.gyw.handwritten.gspring.demo.service..*Service..*(.*)";
		String pointCut = str.replaceAll("\\.", "\\\\.").replaceAll("\\\\.\\*", ".*").replaceAll("\\(", "\\\\(")
				.replaceAll("\\)", "\\\\)");
		System.out.println(">>" + pointCut);
		Pattern pattern = Pattern.compile(pointCut);
		Assertions.assertTrue(pattern.matcher(
				"public java.lang.String cn.gyw.handwritten.gspring.demo.service.HelloService.sayHello(java.lang.String)")
				.matches());
	}

	@Test
	public void test2() {
		String regex = "/hello/say*".replaceAll("\\*", ".*");
		System.out.println(">>" + regex);
		Pattern pattern = Pattern.compile(regex);
		Assertions.assertTrue(pattern.matcher("/hello/say123").matches());
		Assertions.assertFalse(pattern.matcher("/hello/sy123").matches());
	}

	@Test
	public void test() {
		StringBuilder builder = new StringBuilder();
		String regex = "\\$\\{[\\w]+\\}";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

		String[] lines = new String[] { "1234${name}", "56789${age}" };
		Matcher matcher;
		String line;
		for (int i = 0, len = lines.length; i < len; i++) {
			line = lines[i];
			matcher = pattern.matcher(line);
			while (matcher.find()) {
				String paramKey = matcher.group();
				paramKey = paramKey.replaceAll("\\$\\{|\\}", "");
				System.out.println("group() :" + paramKey);
				line = matcher.replaceFirst("0");
				matcher = pattern.matcher(line);
			}
			builder.append(line);
		}

		System.out.println(builder.toString());
	}
}
