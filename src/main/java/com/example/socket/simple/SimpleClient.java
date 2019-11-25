package com.example.socket.simple;

import java.net.Socket;
import java.util.concurrent.CountDownLatch;

import com.example.socket.SocketClient;

public class SimpleClient {
	
	 CountDownLatch countDownLatch=new CountDownLatch(20);
	
	 public SimpleClient() {  
		  try {  
		   // 向本机的2121端口发出客户请求  
			countDownLatch.countDown();
			countDownLatch.await();
			Socket socket = new Socket("localhost", 2121);
			System.out.println("Established a connection...");
	
		   socket.close(); // 关闭Socket  
		  } catch (Exception e) {  
		   System.out.println("Error. " + e);  
		  }  
		 }  
	 
	 /** 
	  * 主方法 
	  *  
	  * @param args 
	  */  
	 public static void main(String[] args) {  
		 while(true){
			 new Thread(new Runnable() {
				@Override
				public void run() {
					 new SocketClient();  
					
				}
			}).start();
			
		 }
	 }    
}
