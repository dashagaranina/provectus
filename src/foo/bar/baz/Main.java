package foo.bar.baz;

import foo.bar.baz.impl.StandaloneSolution;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static void main(String[] args) {
        Solution solution = new StandaloneSolution();
        System.out.println(solution.leibnizPi(5));
    }
}
