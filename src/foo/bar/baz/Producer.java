package foo.bar.baz;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Producer implements Runnable {

	private BlockingQueue<Integer> blockingQueue;

	private Boolean calculated;

	public Producer(BlockingQueue<Integer> blockingQueue) {
		this.calculated = false;
		this.blockingQueue = blockingQueue;
	}

	public void complete() {
		System.out.println("Completed from producer");
		this.calculated = true;
	}

	@Override
	public void run() {
		int n = 1;
		System.out.println("start producer");
		do {
			try {
				blockingQueue.offer(n, 1000, TimeUnit.MILLISECONDS);
				n = n + 1;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (!calculated);
		System.out.println("Producer finished work");

	}
}
