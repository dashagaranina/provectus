package foo.bar.baz;

import foo.bar.baz.impl.StandaloneSolution;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {

	public static void main(String[] args) {

		Solution solution = new StandaloneSolution();
		try {
            System.out.print("Enter Pi accuracy: ");
            Scanner scanner = new Scanner(System.in);
            Integer accuracy = scanner.nextInt();

            long start = System.currentTimeMillis();

			CompletableFuture<BigDecimal> future = solution.leibnizPi(accuracy);
			BigDecimal result = future.get().setScale(accuracy, RoundingMode.DOWN);

			System.out.println("Result: " + result);

			long end = System.currentTimeMillis();
			System.out.println("Time spend: " + (end-start));

		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}
