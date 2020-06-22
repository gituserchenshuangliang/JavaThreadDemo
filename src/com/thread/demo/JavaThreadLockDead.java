package com.thread.demo;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Lock接口中的tryLock()方法解决锁死问题
 * @author Cherry
 * 2020年4月13日
 */
public class JavaThreadLockDead {

	public static void main(String[] args) {
//		Resource r1 = new Resource("R1");
//		Resource r2 = new Resource("R2");
		Resources r1 = new Resources("R1",2);
		Resources r2 = new Resources("R2",3);
		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				r1.getSource(r2);
			}
		}).start();
		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				r2.getSource(r1);
			}
		}).start();
	}

}
//使用Lock解决锁死问题
class Resource{
	private ReentrantLock rl = new ReentrantLock();
	private String name;
	public Resource(String name) {
		this.name = name;
	}
	void getSource(Resource r) {
		while(true) {
			try {
				if(lockSource(r)) {
					System.out.printf("%s,%s all Locked !%n",this.name,r.name);
					break;
				}
			}
			finally {
				unlockSource(r);
			}
		}
	}
	private boolean lockSource(Resource r) {
		return this.rl.tryLock() && r.rl.tryLock();
	}
	private void unlockSource(Resource r) {
		if(this.rl.isHeldByCurrentThread()) {
			this.rl.unlock();
		}
		if(r.rl.isHeldByCurrentThread()) {
			r.rl.unlock();
		}
	}
}
//有几率出现锁死
class Resources{
	private String name;
	private int i;
	public Resources(String name,int i) {
		this.name = name;
		this.i = i;
	}
	synchronized void getSource(Resources r) {
			System.out.printf("%s,%d all Locked !%n",this.name,r.getI());
	}
	synchronized int getI() {
		return ++i;
	}
}