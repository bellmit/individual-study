package cn.gyw.corejava.concurrent.threadpool;

import cn.gyw.corejava.concurrent.ExecutorServiceInfoService;

import java.util.concurrent.*;

/**
 * 线程饥饿死锁
 *
 * 依赖任务相互等待
 */
public class ThreadDeadLock {

    // 单线程安全
    private ExecutorService taskExec = Executors.newSingleThreadExecutor();

    private class RenderPageTask implements Callable<String> {
        @Override
        public String call() throws ExecutionException, InterruptedException {

            Future<String> header,footer;
            // 获取页眉
            header = taskExec.submit(new LoadFileTask("header.html"));
            // 获取页脚
            footer = taskExec.submit(new LoadFileTask("footer.html"));

            // 绘制页面
            String page = renderPage();

            // 等待页眉、页脚返回，组装成最终的页面返回
            return header.get() + page + footer.get();
        }
    }

    private String renderPage() {

        return "";
    }


    private class LoadFileTask implements Callable<String> {

        private String htmlName;

        LoadFileTask(String htmlName) {
            this.htmlName = htmlName;
        }

        @Override
        public String call() {


            return "TODO";
        }
    }

    public static void main(String[] args) {
        ThreadDeadLock threadDeadLock = new ThreadDeadLock();
        ExecutorServiceInfoService infoService = new ExecutorServiceInfoService(threadDeadLock.taskExec);
        infoService.start();

        threadDeadLock.taskExec.submit(threadDeadLock.new RenderPageTask());
    }
}
