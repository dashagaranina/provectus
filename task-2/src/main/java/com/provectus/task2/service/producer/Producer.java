package com.provectus.task2.service.producer;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private BlockingQueue<Integer> blockingQueue;
    private Integer accuracy;

    public Producer(BlockingQueue<Integer> blockingQueue, Integer accuracy) {
        this.blockingQueue = blockingQueue;
        this.accuracy = accuracy;
    }

    @Override
    public void run() {
        System.out.println("Producer started. Thread: " + Thread.currentThread().getName());
        int n = 1;
        int lim = (int) (Math.pow(10, accuracy) * 2 + 12);

        while (n <= lim) {
            try {
                blockingQueue.put(n);
                n++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Producer finished. Thread: " + Thread.currentThread().getName());

    }
}
