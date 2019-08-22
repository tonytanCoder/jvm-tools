package com.example.jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * @author LS-0323
 *
 */
public class HeapOOM {
	 public static void main(String[] args) {
	        List<OOMObject> list = new ArrayList<OOMObject>();
	        while(true) {
	            // list保留引用，避免Full GC 回收 
	            list.add(new OOMObject());
	        }
	    }

	    static class OOMObject {

	    }
}
