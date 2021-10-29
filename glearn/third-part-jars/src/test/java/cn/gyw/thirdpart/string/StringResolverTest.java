package cn.gyw.thirdpart.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO
 *
 * @date 2021/10/29 13:30
 */
class StringResolverTest {

    /**
     * 首尾空格空格
     */
    @Test
    void testBlank() {
        String str = "       ";
        assertEquals("", StringResolver.trimAndRmLineSeparator(str));
        assertNull(StringResolver.trimAndRmLineSeparator(null));
        str = "";
        assertEquals("", StringResolver.trimAndRmLineSeparator(str));
        str = " 1234";
        assertEquals("1234", StringResolver.trimAndRmLineSeparator(str));
        str = "1234 ";
        assertEquals("1234", StringResolver.trimAndRmLineSeparator(str));
        str = " 1234 ";
        assertEquals("1234", StringResolver.trimAndRmLineSeparator(str));
        str = " 中文测试";
        assertEquals("中文测试", StringResolver.trimAndRmLineSeparator(str));
        str = "中文测试 ";
        assertEquals("中文测试", StringResolver.trimAndRmLineSeparator(str));
        str = " 中文测试 ";
        assertEquals("中文测试", StringResolver.trimAndRmLineSeparator(str));
    }

    /**
     * 换行符
     */
    @Test
    void trimAndRmLineSeparator() {
        String str = "hello，中文测试";
        assertEquals("hello，中文测试", StringResolver.trimAndRmLineSeparator(str));
        str = "hello，\n" +
                "中文测试\n" +
                "this is test\n" +
                "!!!";
        assertEquals("hello，中文测试this is test!!!", StringResolver.trimAndRmLineSeparator(str));
        str = "  \n  hello，\n" +
                "中文测试 \n" +
                " this is test\n" +
                "!!!";
        assertEquals("hello，中文测试  this is test!!!", StringResolver.trimAndRmLineSeparator(str));
        str = "  \r  hello，\r" +
                "中文测试 \r" +
                " this is test\r" +
                "!!!";
        assertEquals("hello，中文测试  this is test!!!", StringResolver.trimAndRmLineSeparator(str));
        str = "  \r\n  hello，\r\n" +
                "中文测试 \r\n" +
                " this is test\r\n" +
                "!!!   \r\n  ";
        assertEquals("hello，中文测试  this is test!!!", StringResolver.trimAndRmLineSeparator(str));
    }
}