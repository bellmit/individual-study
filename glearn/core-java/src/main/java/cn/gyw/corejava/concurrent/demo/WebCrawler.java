package cn.gyw.corejava.concurrent.demo;

import cn.gyw.platform.annotations.GuardedBy;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 网页爬虫运用
 *
 * 需要记录被取消的任务，以便下次运行时再将取消的任务加入任务队列
 *
 * @see TrackingExecutor
 */
public abstract class WebCrawler {

    private volatile TrackingExecutor exec;

    private final static long TIMEOUT = 3;
    private final static TimeUnit UNIT = TimeUnit.MINUTES;

    @GuardedBy("this")
    private final Set<URL> urlsToCrawl = new HashSet<>();

    public synchronized void start() {
        exec = new TrackingExecutor(Executors.newCachedThreadPool());
        for (URL url : urlsToCrawl) {
            submitCrawlTask(url);
        }
        urlsToCrawl.clear();
    }

    public synchronized void stop() throws InterruptedException {
        try {
            saveUncrawled(exec.shutdownNow());
            if (exec.awaitTermination(TIMEOUT, UNIT)) {
                saveUncrawled(exec.getCancelledTasks());
            }
        } finally {
            exec = null;
        }
    }

    protected abstract List<URL> processPage(URL url);

    // 提交爬虫任务
    private void submitCrawlTask(URL url) {
        exec.execute(new CrawlTask(url));
    }

    // 保存未爬取的任务
    private void saveUncrawled(List<Runnable> uncrawledTasks) {
        for (Runnable task : uncrawledTasks) {
            urlsToCrawl.add(((CrawlTask) task).getPage());
        }
    }

    private class CrawlTask implements Runnable {

        private final URL url;

        CrawlTask(URL url) {
            this.url = url;
        }

        @Override
        public void run() {
            for (URL link : WebCrawler.this.processPage(url)) {
                if (Thread.currentThread().isInterrupted()) {
                    return;
                }
                submitCrawlTask(link);
            }
        }

        public URL getPage() {
            return url;
        }
    }
}
