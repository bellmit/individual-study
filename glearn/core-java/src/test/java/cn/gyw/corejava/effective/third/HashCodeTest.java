package cn.gyw.corejava.effective.third;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * HashCode 测试
 */
public class HashCodeTest {

    /**
     * HashMap 存放失败
     *
     * 两个对象虽然equals相等，但是hashcode没有重写，是不同的
     * hashmap的优化是，每个项相关联的散列码缓存，如果散列码不匹配，就不必判断等同性；
     */
    @Test
    public void shouldErrorByHashMap() {
        Map<PhoneNumber, String> datas = new HashMap<>();
        datas.put(new PhoneNumber(707, 867, 309), "jenny");
        System.out.println(datas.get(new PhoneNumber(707, 867, 309)));
    }
}
