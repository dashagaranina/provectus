package com.provectus.task2.service.impl;

import com.provectus.task2.client.wrapper.WorkerWrapper;
import com.provectus.task2.model.Result;
import com.provectus.task2.repository.ResultRepository;
import com.provectus.task2.service.Solution;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class DistributedSolution implements Solution {

    @Value("${worker.service.count}")
    private Integer WORKER_SERVICE_COUNT;

    private final ResultRepository repository;

    private final WorkerWrapper worker;

    @Override
    public Integer leibniz(Integer accuracy) {

        Result save = repository.save(new Result(null, accuracy, 0));

        long start = System.currentTimeMillis();

        long lim = (long) (Math.pow(10, accuracy) * 2 + 12);
        int n = (int) (lim / WORKER_SERVICE_COUNT);

        List<CompletableFuture<BigDecimal>> list = new ArrayList<>();

        int left = 1;
        for (int i = 1; i < WORKER_SERVICE_COUNT + 1; i++) {
            int right = i * n;
            log.info("Worker#{} request#{}, left = {}, right = {}", i, save.getId(), left, right);
            list.add(worker.wrap(left, right));
            left = right + 1;
        }

        list.stream().reduce(CompletableFuture
                .completedFuture(BigDecimal.ZERO), (f1, f2) -> f1
                .thenCombineAsync(f2, BigDecimal::add))
                .thenAcceptAsync(c -> {
                    BigDecimal temp1 = BigDecimal.valueOf(2)
                            .multiply(BigDecimal.valueOf(lim))
                            .subtract(BigDecimal.ONE); // 2*lim-1

                    BigDecimal last = BigDecimal.valueOf(4).divide(temp1, 100, RoundingMode.DOWN);  //  4 / (2*lim-1)

                    BigDecimal pi = c.add(c.add(last))
                            .divide(BigDecimal.valueOf(2), 100, RoundingMode.DOWN)          //  sumFromWorkers + last / 2
                            .setScale(accuracy, RoundingMode.DOWN);

                    long end = System.currentTimeMillis();

                    save.setResult(pi.toString());
                    save.setTimeSpend((int) (end - start));
                    Result result = repository.save(save);

                    log.info("Request#{} result saved: {}", result.getId(), result.toString());
                })
                .exceptionally(e -> {
                    log.error(e.getLocalizedMessage());
                    repository.delete(save);
                    throw new RuntimeException(e);
                });

        return save.getId();
    }

    @Override
    public Result getResult(Integer id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Result with ID = " + id + " is not exists"));
    }

}
