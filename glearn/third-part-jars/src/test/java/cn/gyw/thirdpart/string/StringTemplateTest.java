package cn.gyw.thirdpart.string;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String 模板测试
 *
 * @date 2021/12/27 14:32
 */
public class StringTemplateTest {

    private String tempalte = "    @javax.annotation.PostConstruct\n" +
            "    public void test() {\n" +
            "        System.out.println(\">> %s:\" + %s);\n" +
            "    }";

    @Test
    public void render() {
        String key = "AAA";
        String prop = "aaa";

        System.out.println(String.format(tempalte, key, prop));
    }

    @Test
    public void fetchAndRender() {
        fetchKeyAndProp();
    }

    public void fetchKeyAndProp() {
        String str = "    @Value(\"${aaa:}\")\n" +
                "    private String aaa;";

        Pattern pattern = Pattern.compile("\"(.*?)\"\\s*private String (.*?);");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            System.out.println(matcher.group());
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
        }

    }

}
