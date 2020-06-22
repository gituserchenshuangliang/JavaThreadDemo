package com.thread.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 线程安全
 * @author Cherry
 * 2020年4月13日
 */
public class ThreadDemoA {

	public static void main(String[] args) {
		nonThreadSafe();
		
	}
	
	/*
	 * 线程竞速情况
	 */
	//线程非安全
	public static void nonThreadSafe() {
		var array = new ArrayList<Integer>();
		new Thread(() -> {
			while(true){
				array.add(0);
			}
		}).start();
		new Thread(() -> {
			while(true){
				array.add(1);
			}
		}).start();
	}
	
	//synchronized锁定方法，对象或区块实现线程安全.监控锁定或内部锁定
	public static synchronized void ThreadSafe() {
		var array = new ArrayList<Integer>();
		new Thread(() -> {
			while(true){
				synchronized(array) {
					array.add(0);
				}
			}
		}).start();
		new Thread(() -> {
			while(true){
				synchronized(array) {
				array.add(1);
				}
			}
		}).start();
		synchronized(array) {
			new Thread(() -> {
				while(true){
					synchronized(array) {
					array.add(3);
					}
				}
			}).start();
		}
	}
	
	//Collections提供打包器将List打包成线程安全的对象
	@SuppressWarnings("unused")
	public static void threadSafeCollection() {
		List<String> safeList = Collections.synchronizedList(new ArrayList<String>());
		Set<Date> safeSet = Collections.synchronizedSet(new HashSet<Date>());
		Map<Integer,String> safeMap = Collections.synchronizedMap(new HashMap<Integer,String>());
		//.......
	}
	
}
//共享可见变量使用volatile声明，即为线程安全变量
class ShareVariable {
	public volatile int i = 1;
	public volatile String s = "Chen";
}
