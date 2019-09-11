package com.example.thread.stop;

public class StopThreadWithFlag extends Thread {
	/*
	 * Setting the volatile variable exit to false
	 */
	private volatile boolean exit = false;

	/*
	 * Call this method to set the exit value to true and stop the thread
	 */
	public void stopThread() {
		exit = true;
	}

	@Override
	public void run() {
		// Keep the task in while loop
		while (!exit) {
			System.out.println("Thread is running....");
		}
		System.out.println("Thread Stopped.... ");
	}

	public static void main(String args[]) {

		StopThreadWithFlag thread = new StopThreadWithFlag();

		// Starting thread
		thread.start();

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		/*
		 * run below method whenever you want to stop the thread
		 */
		thread.stopThread();
	}
}