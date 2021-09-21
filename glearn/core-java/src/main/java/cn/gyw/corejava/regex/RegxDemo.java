package cn.gyw.corejava.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegxDemo {
    public static void main(String[] args) {

        //要验证的字符串
        String str = "  www.1306501974@qq.com";

        //邮箱验证规则
        String emailEx = "w{3}?\\w*[@][q]{2}";

        //编译正则表达式
        //Pattern pattern = Pattern.compile(emailEx);
        //忽略大小写的写法
        Pattern pattern = Pattern.compile(emailEx, Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(str);

        //查找字符串中是否有匹配正则表达式的字符/字符串
        boolean bresult = matcher.find();

        System.out.println("正则匹配结果：" + bresult);
    }
}
