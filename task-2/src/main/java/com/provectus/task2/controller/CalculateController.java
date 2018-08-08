package com.provectus.task2.controller;

import com.provectus.task2.model.Test;
import com.provectus.task2.service.Calculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class CalculateController {

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private Calculator calculator;

    @RequestMapping(value = "/pi")
    public Test pi (@RequestParam(value = "accuracy", defaultValue = "5") Integer accuracy) {
        long start = System.currentTimeMillis();
        String result = calculator.calculatePi(accuracy);
        long end = System.currentTimeMillis();
        return new Test(counter.incrementAndGet(), result, (end - start));
    }
}
