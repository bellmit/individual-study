package cn.gyw.spring.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * logback 1.1.2 版本bug 验证
 *
 * logback 会因为线程interrupt()，导致日志不会输出到文件中
 *
 * stdout 不会影响
 *
 * 解决方案：提升版本 1.1.3
 */
public class LogbackBugTest {

    static ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(5);

    static ScheduledExecutorService singleThreadPool = Executors.newSingleThreadScheduledExecutor();

    static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {

        System.out.println("start ----");
        // 初始化logback
        initLogback();

        ARunnable aRunnable1 = new ARunnable("a-n-1", false, -1);
        ARunnable aRunnable2 = new ARunnable("a-n-2", true, 5);

        BRunnable bRunnable1 = new BRunnable("b-n-1", false, -1);
        BRunnable bRunnable2 = new BRunnable("b-n-2", true, 7);

        // 以下三种情况控制台输出基本一致（被interrupt后仍然会继续打印日志）

        // 1.interrupt后就不打日志了
        // testUseScheduledThreadPool(aRunnable1, aRunnable2);
        // 2.interrupt后就不打日志了
        // testUseThread(bRunnable1,bRunnable2);
        // 3.interrupt后就不打日志了
        // testFixedThreadPool(bRunnable1, bRunnable2);
        // 4.模拟线程创建定时任务后，线程结束
        testThreadOver(aRunnable1, aRunnable2);
    }

    /**
     * 测试线程结束后，线程池的日志打印状况
     */
    public static void testThreadOver(Runnable runnable1, Runnable runnable2) {
        Thread thread = new Thread(() -> {
            MDC.put("taskId", "thread-111");
            threadPool.scheduleAtFixedRate(runnable1, 1, 20, TimeUnit.MILLISECONDS);
        });
        thread.start();

        try {
            TimeUnit.SECONDS.sleep(3L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(">> 3秒后中断线程");
        thread.interrupt();
    }

    /**
     * 使用线程池ScheduledThreadPool测试
     */
    public static void testUseScheduledThreadPool(Runnable aRunnable1, Runnable aRunnable2) {
        threadPool.scheduleAtFixedRate(aRunnable1, 1, 20, TimeUnit.MILLISECONDS);
        threadPool.scheduleAtFixedRate(aRunnable2, 1, 20, TimeUnit.MILLISECONDS);
    }

    /**
     * 使用Thread线程测试
     */
    public static void testUseThread(Runnable runnable1, Runnable runnable2) {
        new Thread(runnable1).start();
        new Thread(runnable2).start();
    }

    public static void testFixedThreadPool(Runnable runnable1, Runnable runnable2) {
        fixedThreadPool.execute(runnable1);
        fixedThreadPool.execute(runnable2);
    }

    /**
     * 线程循环调度
     */
    public static class ARunnable implements Runnable {

        private final static Logger LOGGER = LoggerFactory.getLogger(ARunnable.class);

        String name;        //线程名称

        int count = 0;      //计数

        boolean isInterrupt = false;    //是否内部中断线程

        int interruptCount;            //count达到  interruptCount  时中断线程

        public ARunnable() {
        }

        public ARunnable(String name, boolean isInterrupt, int interruptCount) {
            this.name = name;
            this.isInterrupt = isInterrupt;
            this.interruptCount = interruptCount;
        }

        @Override
        public void run() {
            if (isInterrupt && interruptCount == count) {
                Thread.currentThread().interrupt();
                LOGGER.warn("A interrupt ... name:" + name + ",count:" + count++);
            } else {
                LOGGER.warn("A name:" + name + ",count:" + count++);
            }
        }
    }

    /**
     * 内部循环执行
     */
    public static class BRunnable implements Runnable {

        private final static Logger LOGGER = LoggerFactory.getLogger(BRunnable.class);

        String name;        //线程名称

        int count = 0;      //计数

        boolean isInterrupt = false;    //是否内部中断线程

        int interruptCount;            //count达到  interruptCount  时中断线程

        public BRunnable() {
        }

        public BRunnable(String name, boolean isInterrupt, int interruptCount) {
            this.name = name;
            this.isInterrupt = isInterrupt;
            this.interruptCount = interruptCount;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (isInterrupt && interruptCount == count) {
                    Thread.currentThread().interrupt();
                    LOGGER.warn("B interrupt ...name:" + name + ",count:" + count++);
                }
                LOGGER.warn("B name:" + name + ",count:" + count++);
            }
        }
    }

    public static void initLogback() {

        URL url = MdcTest.class.getClassLoader().getResource("logback.xml");
        assert url != null;
        System.out.println("Url path :" + url.toString());
        String filePath = null;
        try {
            filePath = Paths.get(url.toURI()).toAbsolutePath().toString();
        } catch (URISyntaxException e) {
            System.out.println("未找到配置文件");
            e.printStackTrace();
        }

        //加载 logback配置信息
        try {
            LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(lc);
            lc.reset();
            configurator.doConfigure(filePath);
            StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
        } catch (JoranException e) {
            e.printStackTrace();
            System.out.println("sync logback.xml error! " + e.getMessage());
            System.exit(0);
        }
    }
}
