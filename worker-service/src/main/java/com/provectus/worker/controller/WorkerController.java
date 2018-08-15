package com.provectus.worker.controller;

import com.provectus.worker.service.Solution;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WorkerController {

    private final Solution solution;


    @GetMapping("/")
    public String hello () {
        return "Hello, man!";
    }

    @GetMapping("/pi")
    public BigDecimal calculate (@RequestParam Integer left,
                                 @RequestParam Integer right){

        BigDecimal result = solution.leibnizPi(left, right);
        log.info("Calculation for [{}, {}] is done. Result {}", left, right, result.setScale(3, RoundingMode.DOWN).toString());
        return result;
    }

}
