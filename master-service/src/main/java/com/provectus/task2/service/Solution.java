package com.provectus.task2.service;

import com.provectus.task2.service.consumer.Consumer;
import com.provectus.task2.service.consumer.Consumer2;
import com.provectus.task2.service.producer.Producer;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

public interface Solution {

    BigDecimal leibniz (Integer accuracy);

    /**
     * Asynchronous calculation Pi by Gregory-Leibniz formula.
     *
     * Calculates the series (4 - 4/3 + 4/5 - 4/7 + 4/9 +- ...) that equals to Sum (+-4 / (2*n - 1))
     * n - value generates by {@link Producer} with step = 1 until n <= (10^accuracy * 2 + 12)
     *
     * In calculations works with {@link Consumer}
     *
     * @param accuracy - the number of decimal places in the calculated Pi
     * @return Pi - expected number which the number of decimal equals to accuracy value
     * @see CompletableFuture
     */
    CompletableFuture<BigDecimal> leibnizPi(Integer accuracy);

    /**
     * Too slow.
     *
     * Asynchronous calculation Pi via Gregory-Leibniz modified by Madhava formula.
     *
     * Pi = sqrt(12) * (1 - 1/ 3 * 3 + 1 / 5 * 3^2 ..) that equals to sqrt(12) * Sum (+- 1 / ((2*n -1) * 3^(n-1)))
     * n - value generates by {@link Producer} with step = 1 until n <= (10^accuracy * 2 + 12)
     *
     * The method is slow because of the multiple operations with {@link BigDecimal}
     * In calculations works with {@link Consumer2}
     *
     * @param accuracy - the number of decimal places in the calculated Pi
     * @return Pi - expected number which the number of decimal equals to accuracy value
     * @see CompletableFuture
     */
    CompletableFuture<BigDecimal> leibnizPi2(Integer accuracy);
}
