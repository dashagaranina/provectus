package foo.bar.baz.impl;

import foo.bar.baz.Solution;

import java.math.BigDecimal;

public class StandaloneSolution implements Solution {

    public Double leibnizPi(Integer accuracy) {
        int n = 0;
        double s1;
        double s2 = 0;
        double epsilon = Math.pow(10, -accuracy);
        do {
            s1 = s2 + calc(++n);
            s2 = s1 - calc(++n);
        } while ((s1 - s2 >= epsilon));
        return (s1 + s2) / 2;
    }

    public synchronized BigDecimal leibnizPi2(Integer accuracy) {
        BigDecimal n = BigDecimal.ZERO;
        BigDecimal s1;
        BigDecimal s2 = BigDecimal.ZERO;
        Double epsilon = Math.pow(10, -accuracy);
        do {
            n = n.add(BigDecimal.ONE);  //n++
            s1 = s2.add(calc(n));       //s1=s2+(4/(2*n-1))
            n = n.add(BigDecimal.ONE);  //n++
            s2 = s1.subtract(calc(n));  //s2=s1-(4/(2*n-1))
        } while ((s1.subtract(s2).doubleValue() >= epsilon));
        BigDecimal result = s1.add(s2);
        return result.divide(BigDecimal.valueOf(2), 100, BigDecimal.ROUND_HALF_UP);
    }


    private double calc(int n) {
        double temp1 = (2 * n - 1);
        return 4 / temp1;
    }

    private synchronized BigDecimal calc(BigDecimal n) {
        BigDecimal temp1 = BigDecimal.valueOf(2).multiply(n).subtract(BigDecimal.ONE); // 2*n-1
        return BigDecimal.valueOf(4).divide(temp1,100, BigDecimal.ROUND_HALF_UP);//4/(2*n-1)
    }
}
