package com.example.socket.simple;

import java.net.ServerSocket;

import com.example.socket.ServerThread;
import com.example.socket.SocketServerDemo;

public class SimpleSocketServer {
	 /** 
	  * 服务器端Socket构造方法 
	  */  
	 public SimpleSocketServer() {  
	  try {  
	  
	   int clientcount = 0; // 统计客户端总数  
	  
	   boolean listening = true; // 是否对客户端进行监听  
	  
	   ServerSocket server = null; // 服务器端Socket对象  
	  
	   try {  
	    // 创建一个ServerSocket在端口2121监听客户请求  
	    server = new ServerSocket(2121,1,null); 
	    
	  
	    System.out.println("Server starts...");  
	   } catch (Exception e) {  
	    System.out.println("Can not listen to. " + e);  
	   }  
	  
	   while (listening) {  
	    // 客户端计数  
	    clientcount++;  
	  
	    // 监听到客户请求,根据得到的Socket对象和客户计数创建服务线程,并启动之  
	    new ServerThread(server.accept(), clientcount).start();  
	   }  
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
		  new SimpleSocketServer();  
		 }  
}
