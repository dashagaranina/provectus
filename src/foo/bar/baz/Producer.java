package foo.bar.baz;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Producer implements Runnable {

    private BlockingQueue<Integer> blockingQueue;

    private boolean calculated;

    public Producer(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void complete() {
        System.out.println("I'm here");
        this.calculated = true;
    }

    @Override
    public void run() {
        int n = 1;
        System.out.println("Start producer from thread: " + Thread.currentThread().getName());
        do {
            try {
                blockingQueue.offer(n, 1000, TimeUnit.MILLISECONDS);
                n = n + 1;
                System.out.println(n);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!calculated);
        System.out.println("Producer finished work " + n);

    }
}
