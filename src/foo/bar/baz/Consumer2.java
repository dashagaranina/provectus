package foo.bar.baz;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Consumer2 implements Callable<BigDecimal> {

    private BlockingQueue<Integer> blockingQueue;
    private BigDecimal sum = BigDecimal.ZERO;

    private boolean exit;

    public void exit() {
        this.exit = true;
    }

    public Consumer2 (BlockingQueue<Integer> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    private BigDecimal prev;

    @Override
    public BigDecimal call() {
        while (true) {
            try {
                Integer n = blockingQueue.poll(1000, TimeUnit.MILLISECONDS);
                System.out.println(n);
                if (n == null) {
                    return sum;
                }
                BigDecimal calc = calc(n);
                if (n % 2 != 0) {
                    sum = sum.add(calc);
                } else {
                    sum = sum.subtract(calc);
                }
                /*if( n == 2000011) {
                    prev = sum;
                    System.out.println("Prev = " + prev);
                }
                if(n == 2000012) {
                    BigDecimal result = sum.add(prev);
                    return result.divide(BigDecimal.valueOf(2), 100, RoundingMode.HALF_UP);
                }*/

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private synchronized BigDecimal calc(Integer n) {
        BigDecimal temp1 = BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(n)).subtract(BigDecimal.ONE); // 2*n-1
        BigDecimal temp2 = BigDecimal.valueOf(3).pow(n-1); //3^n-1
        BigDecimal temp3 = temp1.multiply(temp2);//(2*n-1)* 3^n-1
        return BigDecimal.ONE.divide(temp3, 1000, RoundingMode.HALF_UP);//1/(2*n-1)* 3^n-1
    }
}
