package cn.gyw.platform.gorm;

import org.junit.jupiter.api.Test;

/**
 * @author yuewu.guan
 * @Description 正则表达式测试
 * @time
 */
public class ReTest {

    @Test
    public void replaceFirstAnd() {
        String sql = "and 1=1";
        System.out.println("<<" + sql);
        sql = sql.replaceFirst("^\\s*and", "");
        System.out.println(">>" + sql);
    }

}
