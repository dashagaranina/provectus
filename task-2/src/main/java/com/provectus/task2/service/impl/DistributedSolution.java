package com.provectus.task2.service.impl;

import com.provectus.task2.service.Solution;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class DistributedSolution implements Solution {

    private final RestTemplate restTemplate;

    @Override
    public BigDecimal leibniz(Integer accuracy) {
        long lim = (long) (Math.pow(10, accuracy) * 2 + 12);
        int n = (int) (lim / 3);
  /*      Map<String, Integer> params = new HashMap<>();
        params.put("left", 1);
        params.put("right", n);*/
        int r = n;
        log.info("Cons2, left = 1, right = {}", r);
        BigDecimal result1 = restTemplate.getForObject("http://localhost:8081/pi?left=1&right="+r, BigDecimal.class);
//        BigDecimal result1 = restTemplate.getForObject("http://localhost:8081/pi", BigDecimal.class, params);

/*        params = new HashMap<>();
        params.put("left", n + 1);
        params.put("right", 2 * n);*/
        r = 2*n;
        int l = n+1;
        log.info("Cons3, left = {}, right = {}", l, r);
        BigDecimal result2 = restTemplate.getForObject("http://localhost:8081/pi?left="+l+"&right="+r, BigDecimal.class);
//        BigDecimal result2 = restTemplate.getForObject("http://localhost:8081/pi", BigDecimal.class, params);


/*        params = new HashMap<>();
        params.put("left", 2 * n + 1);
        params.put("right", (int) lim);*/
        r = (int) lim;
        l = (2*n+1);
        log.info("Cons1, left = {}, right = {}", l, r);
        BigDecimal result3 = restTemplate.getForObject("http://localhost:8081/pi?left="+l+"&right="+r, BigDecimal.class);
//        BigDecimal result3 = restTemplate.getForObject("http://localhost:8081/pi", BigDecimal.class, params);

        BigDecimal c = result1.add(result2).add(result3);
        BigDecimal temp1 = BigDecimal.valueOf(2)
                .multiply(BigDecimal.valueOf(lim))
                .subtract(BigDecimal.ONE);
        BigDecimal last = BigDecimal.valueOf(4).divide(temp1, 100, RoundingMode.DOWN);
        BigDecimal result = c.add(c.add(last));
        return result.divide(BigDecimal.valueOf(2), 100, RoundingMode.DOWN).setScale(accuracy, RoundingMode.DOWN);
    }

    @Override
    public CompletableFuture<BigDecimal> leibnizPi(Integer accuracy) {
        return null;
    }

    @Override
    public CompletableFuture<BigDecimal> leibnizPi2(Integer accuracy) {
        return null;
    }
}
