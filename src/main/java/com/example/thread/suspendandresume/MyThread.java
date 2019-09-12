package com.example.thread.suspendandresume;

import java.util.concurrent.locks.LockSupport;

public class MyThread {
	private static final Object lock = new Object();

	private static class Producer extends Thread {
		Producer() {
			super.setName("producer");
		}

		@Override
		public void run() {
			synchronized (lock) {
				System.out.println("in producer");
				Thread.currentThread().suspend();
				System.out.println("producer is resume");
			}
		}
	}

	private static class Consumer extends Thread {
		Consumer() {
			super.setName("consumer");
		}

		@Override
		public void run() {
			synchronized (lock) {
				System.out.println("in consumer");
				Thread.currentThread().suspend();
				System.out.println("consumer is resume");
			}
		}
	}

	static Producer producer = new Producer();
	static Consumer consumer = new Consumer();

	public static void main(String args[]) throws InterruptedException {
		producer.start();
		// 此处是为了让producer先执行，防止producer的resume先于suspend执行。
		Thread.sleep(2000);
		consumer.start();
		producer.resume();
		consumer.resume();
		producer.join();
		consumer.join();
		System.out.println("All shop");
		LockSupport  LockSupport ;
	}
}