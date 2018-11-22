package foo.bar.baz.impl;

import foo.bar.baz.Solution;
import foo.bar.baz.Consumer;
import foo.bar.baz.Consumer2;
import foo.bar.baz.Producer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class StandaloneSolution implements Solution {

    private BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(1000);
    private ExecutorService executor = Executors.newFixedThreadPool(10);

    @Override
    public CompletableFuture<BigDecimal> leibnizPi(Integer accuracy) {
        printCurrentThread();
        producer(accuracy);
        CompletableFuture<BigDecimal> future = consumer(accuracy);
        shutdown();
        return future;
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
    private void producer(Integer accuracy) {
        Producer producer = new Producer(blockingQueue, accuracy);
        CompletableFuture.runAsync(producer, executor);
    }

    /**
     * Starts Consumers in executor service thread pool
     * Using by {@link #leibnizPi(Integer)}
     * Async
     *
     * @param accuracy - the number of decimal places in the calculated Pi
     * @return Pi
     */
    private CompletableFuture<BigDecimal> consumer(Integer accuracy) {
        return Stream.of(1, 2, 3, 4)
                .map(x -> CompletableFuture.supplyAsync(new Consumer(blockingQueue)::call, executor))
                .reduce(CompletableFuture.completedFuture(BigDecimal.ZERO), (f1, f2) -> f1.thenCombineAsync(f2, BigDecimal::add))
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

    /**
     * Starts Consumers in executor service thread pool
     * Using by {@link #leibnizPi2(Integer)}
     * Async
     *
     * @return Pi
     */
    private CompletableFuture<BigDecimal> consumer2() {
        return Stream.of(1, 2, 3, 4)
                .map(x -> CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor))
                .reduce(CompletableFuture.completedFuture(BigDecimal.ZERO), (f1, f2) -> f1.thenCombineAsync(f2, BigDecimal::add))
                .thenApplyAsync(bigDecimal -> bigDecimal.multiply(BigDecimal.valueOf(Math.sqrt(12))));
    }

    /**
     * Shutdown the {@link #executor}
     */
    private void shutdown() {
        executor.shutdown();
    }


    private void printCurrentThread() {
        System.out.println("Current thread: " + Thread.currentThread().getName());
    }
}
