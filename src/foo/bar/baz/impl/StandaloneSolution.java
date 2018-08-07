package foo.bar.baz.impl;

import foo.bar.baz.Consumer2;
import foo.bar.baz.Producer2;
import foo.bar.baz.Solution;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StandaloneSolution implements Solution {

    private BlockingQueue<BigInteger> blockingQueue = new ArrayBlockingQueue<>(1000);

    @Override
    public CompletableFuture<BigDecimal> leidnizPi(Integer accuracy) {

        System.out.println("Current thread: " + Thread.currentThread().getName());
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Producer2 producer = new Producer2(blockingQueue, accuracy);
        Consumer2 consumer = new Consumer2(blockingQueue);
        CompletableFuture
                .runAsync(producer, executor);
//                .thenAccept(a -> consumer.exit());

        CompletableFuture<BigDecimal> future = CompletableFuture.supplyAsync(consumer::call, executor);

//        CompletableFuture<BigDecimal> future1 = CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor);
//        CompletableFuture<BigDecimal> future2 = CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor);
//        CompletableFuture<BigDecimal> future3 = CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor);
        /*List<CompletableFuture<BigDecimal>> futures = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            futures.add(CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor));
        }*/
//        CompletableFuture<BigDecimal> future = futures.get(0).thenCombineAsync(futures.get(1), BigDecimal::add)
//                .thenCombineAsync(futures.get(2), BigDecimal::add);//

//   CompletableFuture<BigDecimal> future = future1.thenCombineAsync(future2, BigDecimal::add).thenCombineAsync(future3, BigDecimal::add);


        executor.shutdown();
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
