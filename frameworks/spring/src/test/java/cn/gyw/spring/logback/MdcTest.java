package cn.gyw.spring.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import org.junit.BeforeClass;
import org.junit.Test;
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
 * MDC
 * Mapped Diagnostic Context，映射调试上下文
 *
 * @date 2021/12/3 10:28
 */
public class MdcTest {

    private static final Logger log = LoggerFactory.getLogger(MdcTest.class);

    // 多线程-线程池
    static ScheduledExecutorService MULTI_POOL = Executors.newScheduledThreadPool(2);
    // 单线程-线程池
    static ScheduledExecutorService SINGLE_POOL = Executors.newScheduledThreadPool(1);

    // 单一线程池，执行两次任务
    ExecutorService executorService = Executors.newFixedThreadPool(1);

    /**
     * 多任务测试 MDC context 获取错乱问题优化
     */
    @Test
    public void multiTaskOptimize() {
        for (int i = 0; i < 10; i++) {
            final String taskId = "taskId-" + i;
            MdcRunnable runnable = new MdcRunnable(i);
            executorService.execute(() -> {
                try {
                    MDC.put("taskId", taskId);
                    runnable.setMdcContext(MDC.getCopyOfContextMap());
                    log.info("创建任务id={}，Thread :{}，taskId={}", runnable.getId(), Thread.currentThread(), taskId);
                    // 任务1秒后执行
                    SINGLE_POOL.schedule(runnable, 1, TimeUnit.SECONDS);
                    log.info("成功创建任务id={}，Thread :{}，MDC context={}", runnable.getId(), Thread.currentThread(), MDC.getCopyOfContextMap());
                } finally {
                    // 清空MDC
                    MDC.clear();
                }
            });
        }
        threadSleep();
    }

    /**
     * 多个任务测试
     * <p>
     * 1、执行线程池coreSize=1
     * 2、调用线程coreSize=1，循环10次，加入延迟任务，每次任务的uuid设置不同，
     * 最终任务执行时，MDC中的uuid 是否符合预期？
     * 结果：
     * 执行线程池，拿到的MDC是创建时，外部调用线程的MDC，线程不重新创建，外部MDC的修改，不会影响当前线程
     * 执行的当前线程，会一直使用，一开始创建的MDC context
     * <p>
     * 解决方案：
     * 可以把MDC中的字段，创建任务时，赋值到字段中，然后在复用的执行线程中手动的修改，执行完成后MDC.clear()
     */
    @Test
    public void multiTask() {
        for (int i = 0; i < 10; i++) {
            final String uuid = "uuid-" + i;
            MdcRunnable runnable = new MdcRunnable(i);
            executorService.execute(() -> {
                try {
                    MDC.put("uuid", uuid);
                    log.info("创建任务id={}，Thread :{}，uuid={}", runnable.getId(), Thread.currentThread(), uuid);
                    // 任务1秒后执行
                    SINGLE_POOL.schedule(runnable, 1, TimeUnit.SECONDS);
                    log.info("成功创建任务id={}，Thread :{}，MDC context={}", runnable.getId(), Thread.currentThread(), MDC.getCopyOfContextMap());
                } finally {
                    // 清空MDC
                    MDC.clear();
                }
            });
        }
        threadSleep();
    }

    /**
     * 1、外部调用线程，MDC.put("uuid", "9999999")
     * 2、线程池中调用MDC.clear()，执行成功
     * 3、外部调用线程，等延迟任务执行完毕后，拿MDC context
     */
    @Test
    public void singleThreadMdc() {
        MdcRunnable runnable = new MdcRunnable(6);
        runnable.setClearMDC(true);
        final String uuid = "uuid-" + 6;
        executorService.execute(() -> {
            try {
                MDC.put("uuid", uuid);
                log.info("Thread :{}，创建任务id={}，uuid={}", Thread.currentThread(),
                        runnable.getId(), uuid);
                // 任务1秒后执行
                SINGLE_POOL.schedule(runnable, 1, TimeUnit.SECONDS);

                // 等待延迟任务执行以后，再去查询MDC 中的uuid
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("Thread :{}，创建任务id={}，MDC context={}", Thread.currentThread(),
                        runnable.getId(), MDC.getCopyOfContextMap());
            } finally {
                // 清空MDC
                MDC.clear();
            }
        });
        threadSleep();
    }


    /**
     * 线程池 + MDC
     */
    @Test
    public void threadPoolMdc() {
        for (int i = 1; i < 1000; i++) {
            MdcRunnable runnable = new MdcRunnable(i);
            executorService.execute(() -> {
                try {
                    String taskId = "thread-" + runnable.getId();
                    MDC.put("taskId", taskId);
                    log.info("Thread :{}，创建任务id={}，taskId={}", Thread.currentThread(),
                            runnable.getId(), taskId);
                    // 任务1秒后执行
                    MULTI_POOL.schedule(runnable, 1, TimeUnit.SECONDS);
                } finally {
                    // 清空MDC
                    MDC.clear();
                }
            });
        }

        // executorService.execute(() -> {
        //     try {
        //         System.out.println("Thread :"+ Thread.currentThread() + "创建任务id=2 , put thread-222");
        //         MDC.put("taskId", "thread-222");
        //         // 任务5秒后执行
        //         threadPool.schedule(runnable2, 3, TimeUnit.SECONDS);
        //     } finally {
        //         // 清空MDC
        //         MDC.clear();
        //     }
        //     System.out.println("创建线程2 MDC context:" + MDC.getCopyOfContextMap());
        // });

        // Thread thread = new Thread(() -> {
        //     try {
        //         MDC.put("taskId", "thread-111");
        //         // 任务3秒后执行
        //         threadPool.schedule(runnable1, 3, TimeUnit.SECONDS);
        //     } finally {
        //         // 清空MDC
        //         MDC.clear();
        //     }
        //     System.out.println("创建线程1 MDC context:" + MDC.getCopyOfContextMap());
        //     // try {
        //     //     TimeUnit.SECONDS.sleep(2L);
        //     // } catch (InterruptedException e) {
        //     //     e.printStackTrace();
        //     // }
        //     // System.out.println("2s后，修改当前线程的taskId，观察线程池中mdc的值");
        //     // MDC.put("taskId", "thread-222");
        // });
        // thread.start();

        // 线程2
        // Thread thread2 = new Thread(() -> {
        //     try {
        //         MDC.put("taskId", "thread-222");
        //         // 任务5秒后执行
        //         threadPool.schedule(runnable2, 5, TimeUnit.SECONDS);
        //     } finally {
        //         // 清空MDC
        //         MDC.clear();
        //     }
        //     System.out.println("创建线程2 MDC context:" + MDC.getCopyOfContextMap());
        // });
        // thread2.start();

        threadSleep();
    }

    @BeforeClass
    public static void setUp() {
        initLogback();
    }

    public void threadSleep() {
        try {
            TimeUnit.HOURS.sleep(1L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载logback配置信息
     */
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
