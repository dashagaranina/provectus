package foo.bar.baz.impl;

import foo.bar.baz.Solution;

public class StandaloneSolution implements Solution {

    public Double leibnizPi(Integer accuracy) {
       int n = 0;
       double s1 = 0;
       double s2 = 0;
       double epsilon = Math.pow(10, -accuracy);
       do  {
           s1 = s2 + calc(++n);
           s2 = s1 - calc(++n);
           System.out.println(s1 - s2+ ", " + epsilon);
       } while ((s1 - s2 >= epsilon));
        return (s1 + s2) / 2;
    }

    private double calc (int n) {
        double temp1 = (2*n - 1);
        return 4 / temp1;
    }
}
