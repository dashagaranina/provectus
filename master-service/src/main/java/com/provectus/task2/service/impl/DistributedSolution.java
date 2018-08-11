package com.provectus.task2.service.impl;

import com.provectus.task2.service.Solution;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class DistributedSolution implements Solution {

    private final RestTemplate restTemplate;

    private final static String URL = "http://localhost:8081/pi";

    @Override
    public BigDecimal leibniz(Integer accuracy) {
        long lim = (long) (Math.pow(10, accuracy) * 2 + 12);
        int n = (int) (lim / 3);
        int l = 1;
        int r = n;
        String url = getUriComponentsBuilder(l, r);

        log.info("Cons1, left = {}, right = {}, URL = {}", l, r, url);
        BigDecimal result1 = restTemplate.getForObject(url,
                BigDecimal.class);

        r = 2 * n;
        l = n + 1;
        url = getUriComponentsBuilder(l, r);
        log.info("Cons2, left = {}, right = {}, URL = {}", l, r, url);
        BigDecimal result2 = restTemplate.getForObject(url, BigDecimal.class);

        r = (int) lim;
        l = (2 * n + 1);
        url = getUriComponentsBuilder(l, r);
        log.info("Cons3, left = {}, right = {}, URL = {}", l, r, url);
        BigDecimal result3 = restTemplate.getForObject(url, BigDecimal.class);

        BigDecimal c = result1.add(result2).add(result3);
        BigDecimal temp1 = BigDecimal.valueOf(2)
                .multiply(BigDecimal.valueOf(lim))
                .subtract(BigDecimal.ONE);
        BigDecimal last = BigDecimal.valueOf(4).divide(temp1, 100, RoundingMode.DOWN);
        BigDecimal result = c.add(c.add(last));

        return result.divide(BigDecimal.valueOf(2), 100, RoundingMode.DOWN).setScale(accuracy, RoundingMode.DOWN);
    }

    private String getUriComponentsBuilder(int left, int right) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("left", left)
                .queryParam("right", right);
        return uriComponentsBuilder.toUriString();
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
