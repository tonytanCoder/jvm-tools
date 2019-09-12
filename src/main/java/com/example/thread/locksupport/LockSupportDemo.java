package com.example.thread.locksupport;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {
 public static void main(String[] args) {
	 System.out.println("...unpark before......");
	 LockSupport.unpark(Thread.currentThread());
	 LockSupport.unpark(Thread.currentThread());
	 System.out.println("...unpark after......");
	 
	 System.out.println("...park before......");
	 LockSupport.park();
	 /*LockSupport.park();*/
	 System.out.println("...park after......");
}
}
