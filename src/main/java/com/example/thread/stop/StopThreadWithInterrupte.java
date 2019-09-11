package com.example.thread.stop;

public class StopThreadWithInterrupte extends Thread {
 
    @Override
    public void run(){
        // Keep the task in while loop    
        while(!Thread.interrupted()) {
            System.out.println("Thread is running....");
        }
        System.out.println("Thread Stopped.... ");
    }

    public static void main(String args[]) { 
 
    	StopThreadWithInterrupte thread = new StopThreadWithInterrupte(); 
        // Starting thread
        thread.start(); 
 
        try {
            Thread.sleep(10);
        }
        catch(InterruptedException e) {
            e.printStackTrace();
        }
 
        /* run below method whenever 
        you want to stop the thread */ 
        thread.interrupt();
    }
}