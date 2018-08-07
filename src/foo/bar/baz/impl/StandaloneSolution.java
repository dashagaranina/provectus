package foo.bar.baz.impl;

import foo.bar.baz.Consumer;
import foo.bar.baz.Consumer2;
import foo.bar.baz.Producer;
import foo.bar.baz.Solution;

import java.math.BigDecimal;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
     * @param accuracy - the number of decimal places in the calculated Pi
     * @return Pi
     */
    private CompletableFuture<BigDecimal> consumer(Integer accuracy) {
        int lim = (int) (Math.pow(10, accuracy) * 2 + 12);

        BigDecimal temp1 = BigDecimal.valueOf(2).multiply(new BigDecimal(lim)).subtract(BigDecimal.ONE);
        BigDecimal last = BigDecimal.valueOf(4).divide(temp1, 100, RoundingMode.HALF_UP);

        CompletableFuture<BigDecimal> future1 = CompletableFuture.supplyAsync(new Consumer(blockingQueue)::call, executor);
        CompletableFuture<BigDecimal> future2 = CompletableFuture.supplyAsync(new Consumer(blockingQueue)::call, executor);
        CompletableFuture<BigDecimal> future3 = CompletableFuture.supplyAsync(new Consumer(blockingQueue)::call, executor);

        return future1
                .thenCombineAsync(future2, BigDecimal::add)
                .thenCombineAsync(future3, BigDecimal::add)
                .thenApplyAsync(c -> {
                    BigDecimal result = c.add(c.add(last));
                    return result.divide(BigDecimal.valueOf(2), 100, RoundingMode.DOWN);
                });
    }

    /**
     * Starts Consumers in executor service thread pool
     * Using by {@link #leibnizPi2(Integer)}
     * Async
     * @return Pi
     */
    private CompletableFuture<BigDecimal> consumer2() {
        CompletableFuture<BigDecimal> future1 = CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor);
        CompletableFuture<BigDecimal> future2 = CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor);
        CompletableFuture<BigDecimal> future3 = CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor);
        CompletableFuture<BigDecimal> future4 = CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor);
        CompletableFuture<BigDecimal> future5 = CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor);
        CompletableFuture<BigDecimal> future6 = CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor);
        CompletableFuture<BigDecimal> future7 = CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor);
        CompletableFuture<BigDecimal> future8 = CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor);
        CompletableFuture<BigDecimal> future9 = CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor);
        CompletableFuture<BigDecimal> future10 = CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor);

        CompletableFuture<BigDecimal> future = future1
                .thenCombineAsync(future2, BigDecimal::add)
                .thenCombineAsync(future3, BigDecimal::add)
                .thenCombineAsync(future4, BigDecimal::add)
                .thenCombineAsync(future5, BigDecimal::add)
                .thenCombineAsync(future6, BigDecimal::add)
                .thenCombineAsync(future7, BigDecimal::add)
                .thenCombineAsync(future8, BigDecimal::add)
                .thenCombineAsync(future9, BigDecimal::add)
                .thenCombineAsync(future10, BigDecimal::add);

        return future.thenApplyAsync(bigDecimal -> bigDecimal.multiply(BigDecimal.valueOf(Math.sqrt(12))));
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
