package cn.gyw.corejava.concurrent.design;
/**
* 子线程循环10次，主线程执行100次，紧接着子线程执行10次，再执行主线程
* 如此往复50次。
* 子线程循环的时候主线程不能打扰，相同的是主线程执行循环的时候子线程也不能打扰
*/
public class ThreadWaitAndNotify {

	public static void main(String[] args) {
		Object object = new Object();
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {

				for(int i = 0;i<50;i++){
					synchronized (object) {
						for(int j =1;j<=10;j++){
							System.out.println(Thread.currentThread().getName() + "的10次循环 ： " + j + "次");
						}
						System.out.println(Thread.currentThread().getName() + "的第" +(i+1)+"次内部10次循环");
						object.notify();
						try {
							object.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		},"子线程");
		thread.start();
		for(int i = 0;i<50;i++){
			synchronized (object) {
				try {
					object.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for(int j =0;j<100;j++){
					System.out.println(Thread.currentThread().getName() + "的100次循环 ： " + j + "次");
				}
				System.out.println(Thread.currentThread().getName() + "的第" +(i+1)+"次内部100次循环");
				object.notify();
			}
		}

	}
}
