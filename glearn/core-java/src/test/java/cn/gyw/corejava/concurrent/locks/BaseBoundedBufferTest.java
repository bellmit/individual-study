package cn.gyw.corejava.concurrent.locks;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 测试有条件的有界缓存
 *
 * @see GrumpyBoundedBuffer
 * @see SleepyBoundedBuffer
 */
public class BaseBoundedBufferTest {

    private GrumpyBoundedBuffer<String> buffer = new GrumpyBoundedBuffer<>(10);

    /**
     * 调用者处理条件的失败
     */
    @Test
    public void testTakeWithBlock() throws InterruptedException {

        // 调用者重试
        while (true) {
            try {
                String result = buffer.take();
                break;
            } catch (Exception e) {
                TimeUnit.SECONDS.sleep(3);
            }
        }

    }
}
