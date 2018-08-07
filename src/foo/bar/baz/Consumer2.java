package foo.bar.baz;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Consumer2 implements Callable<BigDecimal> {

    private BlockingQueue<BigInteger> blockingQueue;
    private BigDecimal sum = BigDecimal.ZERO;

    private boolean exit;

    public void exit() {
        exit = true;
    }

    public Consumer2(BlockingQueue<BigInteger> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    private BigDecimal prev;

    @Override
    public BigDecimal call() {
        while (true) {
            try {
                BigInteger n = blockingQueue.poll(1000, TimeUnit.MILLISECONDS);
                if (n == null) {
                    return sum;
                }
                BigDecimal calc = calc(n);
                if (n.divide(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO) != 0) {
                    sum = sum.add(calc);
                } else {
                    sum = sum.subtract(calc);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private synchronized BigDecimal calc(BigInteger n) {
        BigDecimal temp1 = BigDecimal.valueOf(2).multiply(new BigDecimal(n)).subtract(BigDecimal.ONE); // 2*n-1
        return BigDecimal.valueOf(4).divide(temp1, 100, RoundingMode.HALF_UP);//4/(2*n-1)
    }
}
