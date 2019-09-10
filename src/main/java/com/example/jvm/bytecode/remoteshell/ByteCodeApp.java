package com.example.jvm.bytecode.remoteshell;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteCodeApp {

 public static void main(String[] args) throws IOException {

	    InputStream is = new FileInputStream("F://实录/栗子/jvm-tools/github/jvm-tools/target/classes/com/example/jvm/bytecode/remoteshell/TestClass.class");
	    byte[] b = new byte[is.available()];
	    is.read(b);
	    is.close();
	   /* JavaClassExecuter.execute(b);*/
	    System.out.println(JavaClassExecuter.execute(b));
}
}
