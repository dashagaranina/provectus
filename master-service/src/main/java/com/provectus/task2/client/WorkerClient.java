package com.provectus.task2.client;


import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "worker")
@RibbonClient(name = "worker")
public interface WorkerClient {

    @GetMapping("/pi")
    BigDecimal calculate (@RequestParam Integer left,
                                             @RequestParam Integer right);
}
