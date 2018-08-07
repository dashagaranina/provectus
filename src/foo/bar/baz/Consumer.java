package foo.bar.baz;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

public class Consumer implements Callable<BigDecimal> {

	private BlockingQueue<Integer> blockingQueue;
	private volatile BigDecimal epsilon;
	private int number;

	private AtomicReference<BigDecimal> s1;
	private AtomicReference<BigDecimal> s2;

	public Consumer(BlockingQueue<Integer> blockingQueue,
					AtomicReference<BigDecimal> s1,
					AtomicReference<BigDecimal> s2,
					Integer accuracy,
					int number) {
		this.blockingQueue = blockingQueue;
		this.epsilon = BigDecimal.valueOf(Math.pow(10, -accuracy));
		this.number = number;
		this.s1 = s1;
		this.s2 = s2;
	}

	@Override
	public BigDecimal call() {
		do {
			try {
				int n = blockingQueue.take();
				//System.out.println("Comsumer " + number + " ,S1 = " + s1 + " , S2 = " + s2);
				//System.out.println("n was taken = " + n + " from consumer " + number);
				BigDecimal calc = calc(BigDecimal.valueOf(n));
				if (n % 2 != 0) {
					//s1 = s2.add(calc);
					s1.set(s2.get().add(calc));       //s1=s2+(4/(2*n-1))
//					System.out.println("s1 from consumer " + number + " = " + s1.get().doubleValue());
				} else {
					//s2 = s1.subtract(calc);
					s2.set(s1.get().subtract(calc));       //s2=s1-(4/(2*n-1))
//					System.out.println("s2 from consumer " + number + " = " + s2.get().doubleValue());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        } while ((s1.get().subtract(s2.get())).compareTo(epsilon) > 0);

        System.out.println("done");
		BigDecimal result = s1.get().add(s2.get());
		BigDecimal divide = result.divide(BigDecimal.valueOf(2), 100, RoundingMode.HALF_UP);
		System.out.println("Consumer " + number + " " + divide.doubleValue());
		return divide;
	}

	private synchronized BigDecimal calc(BigDecimal n) {
		BigDecimal temp1 = BigDecimal.valueOf(2).multiply(n).subtract(BigDecimal.ONE); // 2*n-1
		return BigDecimal.valueOf(4).divide(temp1, 100, RoundingMode.HALF_UP);//4/(2*n-1)
	}
}
