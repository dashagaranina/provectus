package foo.bar.baz;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;

public class Producer implements Runnable {

    private BlockingQueue<Integer> blockingQueue;

    public Producer(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        int n = 1;
        System.out.println("start producer");
        do {
            try {
                blockingQueue.put(n);
                n = n+1;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (true);

    }
}
