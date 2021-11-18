package cn.gyw.components.web.utils;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * 正则工具类测试
 *
 * @author yuewu.guan
 * @date 2021/11/18 15:44
 */
public class RegexUtilTest {

    @Test
    public void getBeginNum() {
        String price = "1200暂时位置0";
        BigDecimal bigDecimal = RegexUtil.getBeginNum(price);
        System.out.println(bigDecimal);
        System.out.println(price.replaceFirst(RegexUtil.REGEX_NUM_OF_BEGIN, ""));
    }
}