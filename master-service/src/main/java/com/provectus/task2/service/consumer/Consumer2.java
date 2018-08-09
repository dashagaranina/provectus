package com.provectus.task2.service.consumer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.BlockingQueue;

public class Consumer2 extends AbstractConsumer {

    public Consumer2 (BlockingQueue<Integer> blockingQueue) {
        super(blockingQueue);
    }

    public BigDecimal calc(Integer n) {
        BigDecimal temp1 = BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(n)).subtract(BigDecimal.ONE).multiply(BigDecimal.valueOf(3).pow(n-1)); // 2*n-1 * 3^n-1
        return BigDecimal.ONE.divide(temp1, 100, RoundingMode.DOWN);//1/(2*n-1)* 3^n-1
    }
}
