package foo.bar.baz;

import foo.bar.baz.impl.StandaloneSolution;

import java.math.BigDecimal;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) {
        Solution solution = new StandaloneSolution();
        try {
            BigDecimal result = solution.leidnizPi(8).get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
