package com.thread.demo;
/**
 * 读取和写入锁定（ReentrantReadWriteLock）
 * 悲观读取和乐观读取（）
 * @author Cherry
 * 2020年4月13日
 */
public class JavaThreadReadWriteLock {
	public static void main(String[] args) {
		//array使用读写锁定设计方式,没有任何读取和写入时才能获得写入锁定，没有任何写入锁定时才能获得读取锁定
		MyArrayList<Integer> array = new MyArrayList<Integer>();
		for (int i = 0; i < 100; i++) {
			array.add(i);
		}
		//写入没有锁定时，多个线程可同时获得读取锁定，提高效率
		new Thread(() -> {
			for (int i = 0; i < 100; i++) {
				array.get(i);
				array.size();
			}
		});
		new Thread(() -> {
			for (int i = 0; i < 100; i++) {
				array.get(i);
			}
		});
	}
}
