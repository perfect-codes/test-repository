package com.perfectcodes.forkjoin;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class MyRecursiveTask extends RecursiveTask<Integer> {

    private int[] arr;
    private static final int MAX_LENGTH = 20;

    public MyRecursiveTask(int[] arr) {
        this.arr = arr;
    }

    protected Integer compute() {
        if (arr.length > MAX_LENGTH) {
            MyRecursiveTask childTask1 = new MyRecursiveTask(Arrays.copyOfRange(arr, 0, arr.length / 2));
            MyRecursiveTask childTask2 = new MyRecursiveTask(Arrays.copyOfRange(arr, 0, arr.length / 2));
            childTask1.fork();
            childTask2.fork();
            return childTask1.join()+childTask2.join();
        } else {
            int sum = 0;
            for (int i : arr) {
                sum += i;
            }
            return sum;
        }
    }
}
