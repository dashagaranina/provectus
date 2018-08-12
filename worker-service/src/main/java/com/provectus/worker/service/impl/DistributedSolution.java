package com.provectus.worker.service.impl;

import com.provectus.worker.service.Calculator;
import com.provectus.worker.service.Solution;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class DistributedSolution implements Solution {

    @Value("${worker.thread}")
    private Integer THREAD_MAX;

    private final Environment environment;

    private final Calculator calculator;


    @Override
    public BigDecimal leibnizPi(Integer left, Integer right) {
        try {
            log.info("Worker: {}, left is {}, right is {}", environment.getProperty("server.port"), left, right);

            Integer n = (right - left) / THREAD_MAX;

            List<CompletableFuture<BigDecimal>> list = new ArrayList<>();

            int tempL = left;

            for (int i = 1; i < THREAD_MAX + 1; i++) {
                int x = tempL;
                int y = i;

                list.add(CompletableFuture.supplyAsync(() -> calculator.calculate(x, (y != THREAD_MAX) ? y * n + left : right)));

                tempL = y * n + left + 1;
            }
            return list.stream().reduce(CompletableFuture
                    .completedFuture(BigDecimal.ZERO), (f1, f2) -> f1
                    .thenCombineAsync(f2, BigDecimal::add)).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new  RuntimeException(e);

        }
    }

}
