package com.perfectcodes.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Test {
    public static void main(String [] args){
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("SMS线程");
                return thread;
            }
        };
        BlockingQueue<Runnable> bq = new ArrayBlockingQueue(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,10,1L, TimeUnit.MINUTES, bq, threadFactory);

        List<String> data = new ArrayList<>(10000);
        for (int i=0;i<10000;i++){
            data.add("元素"+i);
        }
        ArrayBlockingQueue dataQueue = new ArrayBlockingQueue(10000);
        dataQueue.addAll(data);
        for (int j=0;j<=10;j++){
            TaskThread t1 = new TaskThread(dataQueue);
            executor.execute(t1);
        }
    }
}
