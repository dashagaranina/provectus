package com.provectus.task2.service;

import java.util.concurrent.CompletableFuture;

public interface Calculator {

    CompletableFuture<String> calculatePi(Integer accuracy);

}
