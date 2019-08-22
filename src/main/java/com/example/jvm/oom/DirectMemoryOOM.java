package com.example.jvm.oom;

import java.nio.ByteBuffer;


/**-XX:MaxDirectMemorySize=10M
 * @author LS-0323
 *
 */
public class DirectMemoryOOM {
	 private static final int _1MB = 1024 * 1024 * 1024;

	    public static void main(String[] args) throws Exception {

	        while ( true ) {
	            // unsafe 直接想操作系统申请内存
	        	ByteBuffer.allocateDirect(_1MB);
	        }
	    }
	    
}
