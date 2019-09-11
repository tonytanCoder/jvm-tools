package com.example.thread.executors;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsDemo {
 public static void main(String[] args) {
	 ExecutorService executorService=Executors.newCachedThreadPool();
	 ExecutorService executorService1=Executors.newSingleThreadExecutor();
	 ExecutorService executorService2=Executors.newScheduledThreadPool(2);
	 ExecutorService executorService3=Executors.newFixedThreadPool(1);
	 
	 Executor executor = Executors.newSingleThreadExecutor();
}
}
