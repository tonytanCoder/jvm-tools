package com.example.jvm.oom;

public class JavaVMStackOOM {
	private void dontStop() {
        while(true) {

        }
    }

    // 多线程方式造成栈内存溢出 OutOfMemoryError
    public void stackLeakByThread() {
        while(true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }

    public static void main(String[] args) {
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLeakByThread();
    }
}
