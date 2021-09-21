package cn.gyw.thirdpart.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExUtil {
	public static boolean match(String regex, String target) {
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(target);
		return m.matches();
	}
}
