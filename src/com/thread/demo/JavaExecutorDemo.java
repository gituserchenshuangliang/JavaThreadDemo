package com.thread.demo;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 线程池
 * @author Cherry
 * 2020年4月13日
 */
public class JavaExecutorDemo {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//ExecutorService有shutdown()和shutdownNow(),isShutdown()
		//缓存线程池
		ExecutorService cache = Executors.newCachedThreadPool();
		//固定线程池
		ExecutorService fixed = Executors.newFixedThreadPool(3);
		
		cache.execute(() -> {
			new Thread().start();;
		});
		
		//Future接口实现可返回结果的线程
		FutureTask<Date> fu = new FutureTask<Date>(() -> {
			return new Date();
		});
		new Thread(fu).start();
		while(!fu.isDone()) {
			System.out.println("doing other things....");
		}
		System.out.println(fu.get().getTime());
		
		//submit()传入Callable接口放回Future对象
		Future<List<String>> fut = cache.submit(() -> {
			return Arrays.asList(new String[] {"chen","Jakc","Mark"});
		});
		//invokeAll()可收集多个Callable返回List<Future>
		List<Callable<String>> list = new LinkedList<Callable<String>>();
		List<Future<String>> li = cache.invokeAll(list);
		
		//具有线程池和排线程功能
		ScheduledThreadPoolExecutor stpe = new ScheduledThreadPoolExecutor(2);
//		stpe.schedule(callable, delay, unit);
//		stpe.schedule(runnable, delay, unit);
		
		
	}

}
