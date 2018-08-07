package foo.bar.baz.impl;

import foo.bar.baz.Consumer2;
import foo.bar.baz.Producer;
import foo.bar.baz.Solution;

import java.math.BigDecimal;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class StandaloneSolution implements Solution {

    private BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(100000);

    private volatile AtomicReference<BigDecimal> s1 = new AtomicReference<>(BigDecimal.ZERO);
    private volatile AtomicReference<BigDecimal> s2 = new AtomicReference<>(BigDecimal.ZERO);

    @Override
    public CompletableFuture<BigDecimal> leidnizPi(Integer accuracy) {

        System.out.println("Current thread: " + Thread.currentThread().getName());

        ExecutorService executor = Executors.newFixedThreadPool(10);
        Producer producer = new Producer(blockingQueue, accuracy);
//        Consumer2 consumer = new Consumer2(blockingQueue);
        CompletableFuture
                .runAsync(producer, executor);
//                .thenAccept(a -> consumer.exit());
        CompletableFuture<BigDecimal> future1 = CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor);
        CompletableFuture<BigDecimal> future2 = CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor);
        CompletableFuture<BigDecimal> future3 = CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor);
        CompletableFuture<BigDecimal> future4 = CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor);
        CompletableFuture<BigDecimal> future5 = CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor);
        CompletableFuture<BigDecimal> future6 = CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor);

        CompletableFuture<BigDecimal> future = future1
				.thenCombineAsync(future2, BigDecimal::add)
				.thenCombineAsync(future3, BigDecimal::add)
				.thenCombineAsync(future4, BigDecimal::add)
				.thenCombineAsync(future5, BigDecimal::add)
				.thenCombineAsync(future6, BigDecimal::add);

        future = future.thenApplyAsync(bigDecimal -> bigDecimal.multiply(BigDecimal.valueOf(Math.sqrt(12))));

		/*List<CompletableFuture<BigDecimal>> list = new ArrayList<>();
		CompletableFuture<BigDecimal> future = null;
		for (int i = 0; i < accuracy; i++) {
			list.add(CompletableFuture.supplyAsync(new Consumer2(blockingQueue)::call, executor));
		}

		for (int i = 0; i < list.size() - 1; i++) {
			if (future!=null){
				future.thenCombineAsync(list.get(i+1), BigDecimal::add);
			} else {
				future = list.get(i).thenCombineAsync(list.get(i+1), BigDecimal::add);
			}
		}*/


        executor.shutdown();
        return future;
    }
}
