package foo.bar.baz;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

public class Consumer implements Callable<BigDecimal> {

    private BlockingQueue<Integer> blockingQueue;
    private volatile Double epsilon;
    private AtomicReference<BigDecimal> s1 = new AtomicReference<>(BigDecimal.ZERO);

    private AtomicReference<BigDecimal> s2 = new AtomicReference<>(BigDecimal.ZERO);


    public Consumer(BlockingQueue<Integer> blockingQueue, Integer accuracy) {
        this.blockingQueue = blockingQueue;
        this.epsilon = Math.pow(10, -accuracy);
    }

    @Override
    public BigDecimal call() {
        do {
            try {
                Integer n = blockingQueue.take();
//                System.out.println("n was taken = " + n);
                if (n%2 != 0) {
                    s1.set(s2.get().add(calc(BigDecimal.valueOf(n))));       //s1=s2+(4/(2*n-1))
//                    System.out.println((s1.get().subtract(s2.get())).doubleValue());
                } else {
                    s2.set(s1.get().subtract(calc(BigDecimal.valueOf(n))));       //s2=s1-(4/(2*n-1))
//                    System.out.println((s1.get().subtract(s2.get())).doubleValue());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while ((s1.get().subtract(s2.get())).doubleValue() >= epsilon);

        System.out.println("done");
        BigDecimal result = s1.get().add(s2.get());
        return result.divide(BigDecimal.valueOf(2), 100, RoundingMode.HALF_UP);
    }

    private synchronized BigDecimal calc(BigDecimal n) {
        BigDecimal temp1 = BigDecimal.valueOf(2).multiply(n).subtract(BigDecimal.ONE); // 2*n-1
        return BigDecimal.valueOf(4).divide(temp1,100, RoundingMode.HALF_UP);//4/(2*n-1)
    }
}
