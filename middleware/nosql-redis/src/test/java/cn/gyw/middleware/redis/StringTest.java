package cn.gyw.middleware.redis;

import org.junit.Test;

import java.util.List;

public class StringTest extends AbstractTest {

    @Test
    public void shouldMget() {
        List<String> result = jedis.mget("name", "age");
        for (String data : result) {
            System.out.println(">>" + data);
        }
    }
}
