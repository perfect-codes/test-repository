package com.perfectcodes;

import com.sun.jmx.snmp.tasks.ThreadService;

import java.util.concurrent.*;

/**
 * Hello world!
 *
 */
public class App 
{
    private static int corePoolSize = 0;
    private static int maximumPoolSize = 4;
    private static long keepAliveTime = 2;

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
//        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("perfect");
                return thread;
            }
        };

        ExecutorService executorService = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,new LinkedBlockingQueue(10), threadFactory, (Runnable r, ThreadPoolExecutor executor) -> {
            System.out.println("线程池饱和");
        });
        for (int i=0;i<10;i++){
            executorService.execute(()->{
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" end " + System.currentTimeMillis());
            });
        }
    }
}
