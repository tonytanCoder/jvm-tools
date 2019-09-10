package com.example.jdk.threadlocal;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadId {
	 // Atomic integer containing the next thread ID to be assigned
	       private static final AtomicInteger nextId = new AtomicInteger(0);
	 
	       // Thread local variable containing each thread's ID
	       private static  ThreadLocal<Integer> threadId =
	           new ThreadLocal<Integer>() {
	              @Override protected Integer initialValue() {
	                  return nextId.getAndIncrement();
	          }
	      };
	 
	      // Returns the current thread's unique ID, assigning it if necessary
	      public static int get() {
	         return threadId.get();
	      }
	      
	      
	      public static void main(String[] args) throws InterruptedException {
	    	  while(true){
	    		  threadId =
	    		           new ThreadLocal<Integer>() {
	    		              @Override protected Integer initialValue() {
	    		                  return nextId.getAndIncrement();
	    		          }
	    		      };
	    		      System.gc();
	    		      Thread.sleep(100L);
	    	  }
	    	  
		}
}
