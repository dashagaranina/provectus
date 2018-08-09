package foo.bar.baz;

import java.math.BigDecimal;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public abstract class AbstractConsumer implements Callable<BigDecimal> {

    private BlockingQueue<Integer> blockingQueue;
    private BigDecimal sum = BigDecimal.ZERO;

    AbstractConsumer(BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public BigDecimal call() {
        System.out.println("Consumer started. Thread: " + Thread.currentThread().getName());
        while (true) {
            try {
                Integer n = blockingQueue.poll(1000, TimeUnit.MILLISECONDS);
                if (n == null) {
                    System.out.println("Consumer finished. Thread: " + Thread.currentThread().getName());
                    return sum;
                }
                BigDecimal calc = calc(n);
                if (n % 2 != 0) {
                    sum = sum.add(calc);
                } else {
                    sum = sum.subtract(calc);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public abstract BigDecimal calc (Integer n);
}
