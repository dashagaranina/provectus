package com.provectus.task2.service.impl;

import com.provectus.task2.client.wrapper.WorkerWrapper;
import com.provectus.task2.model.Result;
import com.provectus.task2.repository.ResultRepository;
import com.provectus.task2.service.Solution;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class DistributedSolution implements Solution {

    private final AtomicInteger counter = new AtomicInteger();

    private final ResultRepository repository;

    private final WorkerWrapper worker;

    @Override
    public Integer leibniz(Integer accuracy) {

        Integer id = counter.incrementAndGet();
        long start = System.currentTimeMillis();

        long lim = (long) (Math.pow(10, accuracy) * 2 + 12);
        int n = (int) (lim / 3);
        int l = 1;
        int r = n;

        log.info("Cons1, left = {}, right = {}", l, r);
        CompletableFuture<BigDecimal> result1 = worker.wrap(l, r);

        r = 2 * n;
        l = n + 1;
        log.info("Cons2, left = {}, right = {}", l, r);
        CompletableFuture<BigDecimal> result2 = worker.wrap(l, r);


        r = (int) lim;
        l = (2 * n + 1);
        log.info("Cons3, left = {}, right = {}", l, r);
        CompletableFuture<BigDecimal> result3 = worker.wrap(l, r);

        result1
                .thenCombineAsync(result2, BigDecimal::add)
                .thenCombineAsync(result3, BigDecimal::add)
                .thenAccept(c -> {
                    log.info("Value from CompletableFuture: {}", c.toString());
                    BigDecimal temp1 = BigDecimal.valueOf(2)
                            .multiply(BigDecimal.valueOf(lim))
                            .subtract(BigDecimal.ONE);
                    BigDecimal last = BigDecimal.valueOf(4).divide(temp1, 100, RoundingMode.DOWN);
                    BigDecimal pi = c.add(c.add(last));
                    pi = pi.divide(BigDecimal.valueOf(2), 100, RoundingMode.DOWN).setScale(accuracy, RoundingMode.DOWN);
                    long end = System.currentTimeMillis();
                    Result save = repository.save(new Result(id, pi.toString(), accuracy, (int) (end - start)));
                    log.info("Result saved: {}", save.toString());
                });

        Result save = repository.save(new Result(id, "result is calculating..", accuracy, 0));
        return save.getId();
    }

}
