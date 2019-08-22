package com.example.jvm.oom;


/**运行时常量池溢出
 * 
 * jdk1.7 及其以后的版本已经“去永久代”，jdk1.6之前的环境下运行才会出现溢出
 * -XX:PermSize=10M -XX:MaxPermSize=10M （间接指定常量池容量大小）
 * jdk1.7及以上版本-XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m -XX:InitialBootClassLoaderMetaspaceSize=10m（JDK8）
 * @author LS-0323
 *
 */
public class RuntimeConstantPoolOOM {
	public static void main(String[] args) {
      /*  List<String> list = new ArrayList<String>();*/
        int i = 0;
        while(true) {
            // list保留引用，避免Full GC 回收 
        	String.valueOf(i++).intern();
           /* list.add();*/
        }
    }
}
