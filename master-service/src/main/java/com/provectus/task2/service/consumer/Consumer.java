package com.provectus.task2.service.consumer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.BlockingQueue;

public class Consumer extends AbstractConsumer {

    public Consumer(BlockingQueue<Integer> blockingQueue) {
        super(blockingQueue);
    }

    public BigDecimal calc(Integer n) {
        BigDecimal temp1 = BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(n)).subtract(BigDecimal.ONE); // 2*n-1
        return BigDecimal.valueOf(4).divide(temp1, 1000, RoundingMode.DOWN);//4/(2*n-1)
    }
}
