package cn.gyw.spring.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MdcRunnable implements Runnable {

    private final static Logger log = LoggerFactory.getLogger(LogbackBugTest.ARunnable.class);

    private int id;

    public boolean clearMDC;

    public Map<String, String> mdcContext;

    public MdcRunnable(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        log.info("开始执行MdcRunnable, id:{}", id);
        // 手动添加MDC
        for (Map.Entry<String, String> entry : mdcContext.entrySet()) {
            MDC.put(entry.getKey(), entry.getValue());
        }

        Map<String, String> contextMap1 = MDC.getCopyOfContextMap();
        System.out.println(id + " MDC context map 1:" + contextMap1);
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("休眠1秒后再次获取MDC上下文...");
        Map<String, String> contextMap2 = MDC.getCopyOfContextMap();
        System.out.println(id + " MDC context map 2:" + contextMap2);
        if (clearMDC) {
            MDC.clear();
            System.out.println(id + " 清空当前线程的MDC context:" + MDC.getCopyOfContextMap());
        }

        MDC.clear();
    }

    public int getId() {
        return id;
    }

    public void setClearMDC(boolean clearMDC) {
        this.clearMDC = clearMDC;
    }

    public void setMdcContext(Map<String, String> mdcContext) {
        this.mdcContext = mdcContext;
    }
}
