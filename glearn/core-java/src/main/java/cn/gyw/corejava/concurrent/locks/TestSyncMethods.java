package cn.gyw.corejava.concurrent.locks;

import java.util.concurrent.CyclicBarrier;

/**
 * java 同步机制
 * 实现方式：
 * 1.Synchronized
 * 2.ReentrantLock
 * 3.Atomic
 *
 * 应用场景：
 * 在锁竞争不激烈的情况下，优先使用synchronized，因为其由JVM实现，编译器优化，而且由JVM释放
 * 竞争激烈的情况下，ReentrantLock这种可以被中断的锁有着很大的稳定性，如果获取不到锁、或者在
 * 固定的时间获取不到锁，自动进行休眠，减少锁竞争的消耗
 *
 * ReentrantLock，配合Condition类，有着更好的灵活性，更有针对性的通知哪些线程，
 * 且是公平锁，所有等待线程需要排队
 *
 * Atomic 只能同步一个域
 * Created by guanyw on 2018/7/12.
 */
public class TestSyncMethods {

	public static void test(int round,int threadNum,CyclicBarrier cyclicBarrier){
        new SyncTest("Sync",round,threadNum,cyclicBarrier).testTime();
        new ReenLockDemo("Lock",round,threadNum,cyclicBarrier).testTime();
    }

    public static void main(String args[]){

        for(int i=0;i<5;i++){
            int round=100000*(i+1);
            int threadNum=5*(i+1);
            CyclicBarrier cb=new CyclicBarrier(threadNum*2+1);
            System.out.println("==========================");
            System.out.println("round:"+round+" thread:"+threadNum);
            test(round,threadNum,cb);

        }
    }
}
