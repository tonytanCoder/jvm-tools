package com.example.collection.queue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayObject implements Delayed { 
    private String name; 
    private long time; 
  
    // Contructor of DelayObject 
    public DelayObject(String name, long delayTime) 
    { 
        this.name = name; 
        this.time = System.currentTimeMillis() 
                    + delayTime; 
    } 
  
    // Implementing getDelay() method of Delayed 
    @Override
    public long getDelay(TimeUnit unit) 
    { 
        long diff = time - System.currentTimeMillis(); 
        return unit.convert(diff, TimeUnit.MILLISECONDS); 
    } 
  
    // Implementing compareTo() method of Delayed 
    @Override
    public int compareTo(Delayed obj) 
    { 
        if (this.time < ((DelayObject)obj).time) { 
            return -1; 
        } 
        if (this.time > ((DelayObject)obj).time) { 
            return 1; 
        } 
        return 0; 
    } 
  
    // Implementing toString() method of Delayed 
    @Override
    public String toString() 
    { 
        return "\n{"
            + "name=" + name 
            + ", time=" + time 
            + "}"; 
    } 
} 