package com.provectus.task2.service.impl;


import com.provectus.task2.service.Solution;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class StandaloneSolution implements Solution {

    private BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(1000);

    @Override
    public CompletableFuture<BigDecimal> leibnizPi(Integer accuracy) {
        printCurrentThread();
        producer(accuracy);
        CompletableFuture<BigDecimal> future1 = consumer(accuracy);
        CompletableFuture<BigDecimal> future2 = consumer(accuracy);
        CompletableFuture<BigDecimal> future3 = consumer(accuracy);

        CompletableFuture.allOf(future1, future2, future3).join();

        return future1
                .thenCombineAsync(future2, BigDecimal::add)
                .thenCombineAsync(future3, BigDecimal::add)
                .thenApplyAsync(c -> {
                    System.out.println("Callback: " + Thread.currentThread().getName());
                    BigDecimal temp1 = BigDecimal.valueOf(2)
                            .multiply(BigDecimal.valueOf((Math.pow(10, accuracy) * 2 + 12)))
                            .subtract(BigDecimal.ONE);
                    BigDecimal last = BigDecimal.valueOf(4).divide(temp1, 100, RoundingMode.DOWN);
                    BigDecimal result = c.add(c.add(last));
                    return result.divide(BigDecimal.valueOf(2), 100, RoundingMode.DOWN);
                });
    }

    @Override
    public CompletableFuture<BigDecimal> leibnizPi2(Integer accuracy) {
        printCurrentThread();
        producer(accuracy);
        CompletableFuture<BigDecimal> future = consumer2();
        shutdown();
        return future;
    }

    /**
     * Starts Producer in executor service thread pool
     * Async
     *
     * @param accuracy - the number of decimal places in the calculated Pi
     */
    @Async("asyncExecutor")
    public void producer(Integer accuracy) {
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

    /**
     * Starts Consumers in executor service thread pool
     * Using by {@link #leibnizPi(Integer)}
     * Async
     *
     * @param accuracy - the number of decimal places in the calculated Pi
     * @return Pi
     */
    @Async("asyncExecutor")
    public CompletableFuture<BigDecimal> consumer(Integer accuracy) {
        BigDecimal sum = BigDecimal.ZERO;
        System.out.println("Consumer started. Thread: " + Thread.currentThread().getName());
        while (true) {
            try {
                Integer n = blockingQueue.poll(1000, TimeUnit.MILLISECONDS);
                if (n == null) {
                    System.out.println("Consumer finished. Thread: " + Thread.currentThread().getName());
                    break;
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
        return CompletableFuture.completedFuture(sum);
    }

    /**
     * Starts Consumers in executor service thread pool
     * Using by {@link #leibnizPi2(Integer)}
     * Async
     *
     * @return Pi
     */
    private CompletableFuture<BigDecimal> consumer2() {
        return CompletableFuture.completedFuture(BigDecimal.ZERO);
//        return Stream.of(1, 2, 3, 4)
//                .map(x -> CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor))
//                .reduce(CompletableFuture.completedFuture(BigDecimal.ZERO), (f1, f2) -> f1.thenCombineAsync(f2, BigDecimal::add))
//                .thenApplyAsync(bigDecimal -> bigDecimal.multiply(BigDecimal.valueOf(Math.sqrt(12))));
    }

    /**
     * Shutdown the {@link #executor}
     */
    private void shutdown() {
//        executor.shutdown();
    }


    private void printCurrentThread() {
        System.out.println("Current thread: " + Thread.currentThread().getName());
    }

    public BigDecimal calc(Integer n) {
        BigDecimal temp1 = BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(n)).subtract(BigDecimal.ONE); // 2*n-1
        return BigDecimal.valueOf(4).divide(temp1, 1000, RoundingMode.DOWN);//4/(2*n-1)
    }
}
