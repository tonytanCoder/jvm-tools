package com.example.thread.imp.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadCallable {
 public static void main(String[] args) throws InterruptedException, ExecutionException {
	 Callable<Integer> oneCallable = new FactorialCalculator(1);   
	//由Callable<Integer>创建一个FutureTask<Integer>对象：   
	FutureTask<Integer> oneTask = new FutureTask<Integer>(oneCallable);   
	//注释：FutureTask<Integer>是一个包装器，它通过接受Callable<Integer>来创建，它同时实现了Future和Runnable接口。 
	  //由FutureTask<Integer>创建一个Thread对象：   
	Thread oneThread = new Thread(oneTask); 
	oneThread.start();   
	System.out.println(oneTask.get());
	//至此，一个线程就创建完成了。
}
}
