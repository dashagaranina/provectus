package com.provectus.worker.service.impl;

import com.provectus.worker.service.Solution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
public class DistributedSolution implements Solution {


    @Override
    public BigDecimal leibnizPi(Integer left, Integer right) {

        BigDecimal sum = BigDecimal.ZERO;

        while (left <= right) {
            BigDecimal calc = calc(left);
            if (left % 2 != 0) {
                sum = sum.add(calc);
            } else {
                sum = sum.subtract(calc);
            }

            left++;
        }

        return sum;
    }

    private BigDecimal calc(Integer n) {
        BigDecimal temp1 = BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(n)).subtract(BigDecimal.ONE); // 2*n-1
        return BigDecimal.valueOf(4).divide(temp1, 1000, RoundingMode.DOWN);//4/(2*n-1)
    }
}
