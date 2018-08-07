package foo.bar.baz;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

public class Producer2 implements Runnable {

    private BlockingQueue<BigInteger> blockingQueue;
    private Integer accuracy;

    public Producer2(BlockingQueue<BigInteger> blockingQueue, Integer accuracy) {
        this.blockingQueue = blockingQueue;
        this.accuracy = accuracy;
    }

    @Override
    public void run() {
        BigInteger n = BigInteger.ONE;
        BigInteger lim = BigInteger.valueOf((long) (Math.pow(10, accuracy) * 2 + 12));

        while (n.compareTo(lim) <= 0) {
            try {
                System.out.println(n);
                blockingQueue.put(n);
                n = n.add(BigInteger.ONE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Producer finished work " + n);

    }
}
