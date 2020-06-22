package com.thread.demo;

/**
 * 线程
 * @author Cherry
 * 2020年4月13日
 */
public class ThreadDemo {
	public static void main(String[] args) {
		//守护线程
//		Thread daemon = new Thread(() -> System.out.println("守护线程在非守护线程执行完后关闭JVM,不会执行守护线程！"));
//		daemon.setDaemon(true);
		
		//线程组
//		ThreadGroup tg = new ThreadGroup("OtherThread");
//		Thread t1 = new Thread(tg,new One());
//		Thread t2 = new Two(tg,"t2");
//		t2.setPriority(5);
//		Thread t3 = new Three();
		
//		t1.start();
//		try {
//			t1.sleep(300);
//			t3.join(500);
//			t3.start();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		t2.start();
//		t3.start();
		
//		System.out.printf("t1所属线程组：%s%n",t1.getThreadGroup().getName());
//		System.out.printf("t2所属线程组：%s%n",t2.getThreadGroup().getName());
//		System.out.printf("t3所属线程组：%s%n",t3.getThreadGroup().getName());
//		System.out.printf("当前线程：%s",Thread.currentThread().getName());
		
		//自定义线程组中未被捕捉到的异常
		ThreadGroup tg1 = new ThreadGroup("aThread") {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.printf("%s线程发生%s%n",t.getName(),e.getMessage());
			};
		};
		new Thread(tg1,() -> {
				throw new RuntimeException("文件没找到！");
			}).start();
		
		ThreadGroup tg2 = new ThreadGroup("bThread");
		Thread t5 = new Thread(tg2,() -> {
			throw new RuntimeException("非法访问！！");
		});
//		t5.setDefaultUncaughtExceptionHandler((Thread t, Throwable e) -> {
//			System.out.printf("%s线程发生%s",t.getName(),e.getMessage());
//		});
		t5.start();
	}

}
class One implements Runnable {

	@Override
	public void run() {
		System.out.println("实现Runnable接口的线程");
	}
	
}
class Two extends Thread {
	Two(ThreadGroup tg,String str){
		super(tg, str);
	}
	@Override
	public void run() {
		System.out.println("继承Thread类的线程");
	}
}
class Three extends Thread {
	
	@Override
	public void run() {
		System.out.println("继承Thread类的线程Three");
	}
}