package com.thread.demo;

import java.util.Arrays;
import java.util.concurrent.locks.StampedLock;

/**
 * 乐观读取
 * @author Cherry
 * 2020年4月13日
 */
public class MyArrayList2<E> {
	private Object[] obj;
	private int next;
	//使用ReadWriteLock
	private StampedLock sl = new StampedLock();
	public MyArrayList2() {
		this(16);
	}
	public MyArrayList2(int capacity) {
		obj = new Object[capacity];
	}
	public void add(E e) {
		//获取写入锁定,wl用于解除锁定和转换成其他锁定tryConvertToXXXX()
		long wl = sl.writeLock();
		try {
			if(obj.length == next) {
				obj = Arrays.copyOf(obj, obj.length*2);
			}
			obj[next++] = e;
		}finally {
			sl.unlock(wl);
		}
	}
	public E get(int i) {
		//获取读取锁定
		long rl = sl.tryOptimisticRead();
		try {
			rl = sl.readLock();
			return (E) obj[i];
		}finally {
			sl.unlockRead(rl);
		}
	}
	public int size() {
		//获取读取锁定,乐观读取
		long rl = sl.tryOptimisticRead();
		try {
			rl = sl.readLock();
			return obj.length;
		}finally {
			sl.unlockRead(rl);
		}
	}
}
