package foo.bar.baz;

import foo.bar.baz.impl.StandaloneSolution;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static void main(String[] args) {
        Solution solution = new StandaloneSolution();
        Double result1 = solution.leibnizPi(5);
        Double result2 = solution.leibnizPi2(5).doubleValue();
        System.out.println("result1 = " + result1);
        System.out.println("result2 = " + result2);
    }
}
