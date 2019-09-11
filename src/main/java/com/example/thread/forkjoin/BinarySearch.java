package com.example.thread.forkjoin;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class BinarySearch {
	int[] arr = new int[100];
    public BinarySearch()
        {
           init();
        }
    private void init()
    {
           for(int i=0; i<arr.length;i++)
           {
                  arr[i]=0;
           }             
           Arrays.sort(arr);
    }      
    public void createForJoinPool(int search)
    {
           ForkJoinPool forkJoinPool = new ForkJoinPool(50);
           ForkJoinSearcher searcher = new ForkJoinSearcher(this.arr,search);
           Boolean status = forkJoinPool.invoke(searcher);             
           System.out.println(" Element ::" + search +" has been found in array? :: " + status );             
    }
    public static void main(String[] args) {
           BinarySearch search = new BinarySearch();
           search.createForJoinPool(10);
           System.out.println("**********************");
           search.createForJoinPool(104);             
    }
}
