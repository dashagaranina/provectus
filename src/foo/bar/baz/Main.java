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
			Integer accuracy = 5;
			CompletableFuture<BigDecimal> future = solution.leidnizPi(accuracy);
			BigDecimal result = future.get().setScale(accuracy, RoundingMode.DOWN);

			System.out.println("Result: " + result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}
