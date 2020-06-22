package com.thread.demo;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * 自定义数组集合
 * @author Cherry
 * 2020年4月13日
 */
public class MyArrayList<E> {
	private Object[] obj;
	private int next;
	//使用ReadWriteLock
	private ReentrantReadWriteLock rrw = new ReentrantReadWriteLock();
	public MyArrayList() {
		this(16);
	}
	public MyArrayList(int capacity) {
		obj = new Object[capacity];
	}
	public void add(E e) {
		//获取写入锁定
		WriteLock wl = rrw.writeLock();
		try {
			wl.lock();
			if(obj.length == next) {
				obj = Arrays.copyOf(obj, obj.length*2);
			}
			obj[next++] = e;
		}finally {
			wl.unlock();
		}
	}
	public E get(int i) {
		//获取读取锁定
		ReadLock rl = rrw.readLock();
		try {
			rl.lock();
			return (E) obj[i];
		}finally {
			rl.unlock();
		}
	}
	public int size() {
		return obj.length;
	}
}
