package foo.bar.baz.impl;

import foo.bar.baz.Consumer;
import foo.bar.baz.Producer;
import foo.bar.baz.Solution;

import java.math.BigDecimal;
import java.util.concurrent.*;

public class StandaloneSolution implements Solution {

    private BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);

    @Override
    public Future<BigDecimal> leidnizPi(Integer accuracy) {

        ExecutorService executor = Executors.newFixedThreadPool(4);

        Future<BigDecimal> future = executor.submit(new Consumer(blockingQueue, accuracy));

        Thread producer = new Thread(new Producer(blockingQueue));

        producer.start();

//TODO need to stop PRODUCER after calc
//        producer.join();

        return future;
    }

    /*
        public synchronized BigDecimal leibnizPi2(Integer accuracy) {
            BigDecimal n = BigDecimal.ZERO;
            BigDecimal s1;
            BigDecimal s2 = BigDecimal.ZERO;
            Double epsilon = Math.pow(10, -accuracy);
            do {
                n = n.add(BigDecimal.ONE);  //n++
                s1 = s2.add(calc(n));       //s1=s2+(4/(2*n-1))
                n = n.add(BigDecimal.ONE);  //n++
                s2 = s1.subtract(calc(n));  //s2=s1-(4/(2*n-1))
            } while ((s1.subtract(s2).doubleValue() >= epsilon));
            BigDecimal result = s1.add(s2);
            return result.divide(BigDecimal.valueOf(2), 100, BigDecimal.ROUND_HALF_UP);
        }*/
   /* private double calc(int n) {
        double temp1 = (2 * n - 1);
        return 4 / temp1;
    }*/
}
