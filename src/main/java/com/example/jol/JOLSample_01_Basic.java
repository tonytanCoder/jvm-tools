package com.example.jol;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import static java.lang.System.out;

public class JOLSample_01_Basic {
	 public static void main(String[] args) throws Exception {
	        out.println(VM.current().details());
	        out.println(ClassLayout.parseClass(A.class).toPrintable());
	    }

	    public static class A {
	        boolean f;
	        String name;
	    }
}
