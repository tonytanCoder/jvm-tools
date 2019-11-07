package com.example.collection.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;

public class DelayQueueDemo {
 public static void main(String[] args) {
	DelayQueue<DelayObject> delayObjects=new DelayQueue<>();
	 // Add numbers to end of DelayQueue 
	delayObjects.add(new DelayObject("A", 1)); 
	delayObjects.add(new DelayObject("B", 2)); 
	delayObjects.add(new DelayObject("C", 3)); 
	delayObjects.add(new DelayObject("D", 4)); 

    // print Dequee 
    System.out.println("DelayQueue: "
                       + delayObjects); 

    // create object of DelayQueue 
    // using DelayQueue(Collection c) constructor 
    BlockingQueue<DelayObject> DQ2 
        = new DelayQueue<DelayObject>(delayObjects); 

    // print Dequee 
    System.out.println("DelayQueue: "
                       + DQ2); 
}
}
