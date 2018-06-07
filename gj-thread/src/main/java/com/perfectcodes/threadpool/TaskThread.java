package com.perfectcodes.threadpool;

import java.lang.reflect.Array;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TaskThread implements Runnable {

    private ArrayBlockingQueue<String> queue;

    public TaskThread(ArrayBlockingQueue<String> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!queue.isEmpty()){
            System.out.println(Thread.currentThread().getName()+"输出："+this.queue.poll());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
