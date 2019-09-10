package com.example.jvm.bytecode.remoteshell;

import java.lang.reflect.Method;

public class JavaClassExecuter {
	 /**
     * 执行外部传过来的代表一个Java类的byte数组
     * 将输入类byte数组中代表 java.lang.System的CONTANT_Utf8_info常量修改为劫持后的HackSystem类
     * 执行方法为该类的 main 方法，输出结构为该类向System.out/err输出的信息
     * @param classByte
     * @return
     */
    public static String execute(byte[] classByte) {
        HackSystem.clearBuffer();
        ClassModifier classModifier = new ClassModifier(classByte);
        //修改Class字节码，把HackSystem 替代 System
        byte[] modiBytes = classModifier.modifyUTF8Constant("java.lang.System", "com.example.jvm.bytecode.remoteshell.HackSystem");
        HotSwapClassLoader loader = new HotSwapClassLoader();
        Class clazz = loader.loadByte(modiBytes);
        try {
            //调用其main方法
            Method method = clazz.getMethod("main", new Class[] { String[].class});
            method.invoke(null, new String[] {null});
        } catch (Exception e) {
            e.printStackTrace(HackSystem.out);
        }
        return HackSystem.getBufferString();
    }

}
