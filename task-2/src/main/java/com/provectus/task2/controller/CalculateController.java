package com.provectus.task2.controller;

import com.provectus.task2.model.Test;
import com.provectus.task2.service.Calculator;
import com.provectus.task2.service.Solution;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CalculateController {

    private final AtomicLong counter = new AtomicLong();

//    private final Calculator calculator;

    @Qualifier("distributedSolution")
    private final Solution solution;

    @RequestMapping(value = "/pi")
    public Test pi (@RequestParam(value = "accuracy", defaultValue = "5") Integer accuracy) {
        long start = System.currentTimeMillis();
//        CompletableFuture<String> result = calculator.calculatePi(accuracy);
        BigDecimal result = solution.leibniz(accuracy);
        long end = System.currentTimeMillis();
        return new Test(counter.incrementAndGet(), result.toString(), (end - start));
    }
}
