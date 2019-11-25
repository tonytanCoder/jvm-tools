package com.example.java.stream;

public class LambdaDemo {
 public static void main(String[] args) {
	 Thread test=new Thread(new Runnable() {
			public void run() {
				System.out.println("init");
			}
		}) ;
	 
	 Runnable test1=()->System.out.println("init");
}
}
