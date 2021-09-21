package cn.gyw.corejava.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 为任务设置时限
 * <p>
 * 模拟场景：在执行时间内获取广告
 */
public class DeadLineFetchAD {

    // 预算时间
    private final long TIME_BUDGET = 3000;

    // 线程池
    private final ExecutorService exec = Executors.newFixedThreadPool(4);

    // 渲染带广告的页面
    public void renderPageWithAd() throws InterruptedException {
        long endNanos = System.nanoTime() + TIME_BUDGET;

        Future<Ad> f = exec.submit(new FetchAdTask());

        // 做其他任务 ...

        Ad ad;
        try {
            // 只需等待指定时长
            long timeLeft = endNanos - System.nanoTime();
            ad = f.get(timeLeft, TimeUnit.NANOSECONDS);
        } catch (ExecutionException e) {
            ad = new Ad();
        } catch (TimeoutException e) {
            ad = new Ad();
            f.cancel(true);
        } catch (InterruptedException e) {
            throw e;
        }

        //
    }

    // 拉取广告的任务
    private class FetchAdTask implements Callable<Ad> {

        @Override
        public Ad call() throws Exception {

            // 模拟任务耗时3s
            TimeUnit.SECONDS.sleep(3);

            return new Ad();
        }
    }

    // 广告类
    private class Ad {

        private int adId;
        private String adContent = "广告内容";
		public int getAdId() {
			return adId;
		}
		public void setAdId(int adId) {
			this.adId = adId;
		}
		public String getAdContent() {
			return adContent;
		}
		public void setAdContent(String adContent) {
			this.adContent = adContent;
		}
		@Override
		public String toString() {
			return "Ad [adId=" + adId + ", adContent=" + adContent + "]";
		}
    }
}
