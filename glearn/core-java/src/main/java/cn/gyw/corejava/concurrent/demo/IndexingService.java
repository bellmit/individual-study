package cn.gyw.corejava.concurrent.demo;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 桌面搜索示例
 */
public class IndexingService {

    // 毒丸对象
    private static final File POISON = new File("");

    private final IndexerThread consumer = new IndexerThread();
    private final CrawlerThread producer = new CrawlerThread();

    private final BlockingQueue<File> queue;
    private final File root;

    public IndexingService() {
        this.queue = new LinkedBlockingQueue<>();
        this.root = new File("/");
    }

    // 启动服务
    public void start() {
        this.consumer.start();
        this.producer.start();
    }

    // 结束服务
    public void stop() {
        this.producer.interrupt();
    }

    // （Crawler：爬行者 ）生产者线程
    class CrawlerThread extends Thread {

        @Override
        public void run() {
            try {
                crawl(IndexingService.this.root);
            } catch (InterruptedException e) {
                /* 发生异常 */
            } finally {
                while (true) {
                    try {
                        IndexingService.this.queue.put(POISON);
                        break;
                    } catch (InterruptedException e) {
                        /* 重新尝试 */
                    }
                }
            }
        }

        // 文件搜索
        private void crawl(File root) throws InterruptedException {
            // 找到文件放入队列中
            IndexingService.this.queue.put(new File("demo"));
        }
    }

    // 消费者线程
    class IndexerThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    File file = IndexingService.this.queue.take();
                    if (file == POISON) {
                        break;
                    } else {
                        indexFile(file);
                    }
                }
            } catch (InterruptedException consumed) {}
        }

        // 文件消费
        private void indexFile(File file) {

        }
    }
}


