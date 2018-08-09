package com.provectus.task2.service.impl;

import com.provectus.task2.service.Calculator;
import com.provectus.task2.service.Solution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class PiCalculator implements Calculator {

    @Autowired
    private Solution solution;

    @Override
    @Async
    public CompletableFuture<String> calculatePi(Integer accuracy) {
        try {

            CompletableFuture<BigDecimal> future = solution.leibnizPi(accuracy);
            BigDecimal decimal = future.get().setScale(accuracy, RoundingMode.DOWN);
            return CompletableFuture.completedFuture(decimal.toString());

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
