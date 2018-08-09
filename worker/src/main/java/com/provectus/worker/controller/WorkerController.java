package com.provectus.worker.controller;

import com.provectus.worker.service.Solution;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

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

        return result;
    }

}
