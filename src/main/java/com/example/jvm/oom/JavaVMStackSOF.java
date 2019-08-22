package com.example.jvm.oom;

/**虚拟机栈和本地方法栈溢出 StackOverFlow
 * VM Args：-Xss128k
 * @author LS-0323
 *
 */
public class JavaVMStackSOF {
	private int stackLength = 1;

    // 递归调用方法，定义大量的本地变量，增大此方法帧中本地变量表的长度
    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length: " + oom.stackLength);
            throw e;
        }
    }
}
