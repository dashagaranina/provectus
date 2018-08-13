package com.provectus.task2.controller;

import com.provectus.task2.model.Result;
import com.provectus.task2.service.StatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/statistic")
@Api(description = "Pi calculation statistic")
public class StatisticController {

    private final StatisticService statistic;

    @GetMapping("/count")
    @ApiOperation("Returns total count of request to Master service (for Pi calculations)")
    public Integer count() {
        return statistic.count();
    }

    @GetMapping("/accuracy/{accuracy}")
    @ApiOperation("Returns total count of request to Master service (for Pi calculations) by accuracy")
    public List<Result> count(@PathVariable("accuracy") Integer accuracy) {
        return statistic.getAllByAccuracy(accuracy);
    }

    @GetMapping("/result")
    @ApiOperation("Returns all results of Pi calculations")
    public List<Result> resultsAll() {
        return statistic.getAll();
    }


    @GetMapping("/slowest")
    @ApiOperation("Returns Top 5 slowest results of Pi calculations")
    public List<Result> slowest() {
        return statistic.getTop5Slowest();
    }

    @GetMapping("/quickest")
    @ApiOperation("Returns Top 5 quickest results of Pi calculations")
    public List<Result> quickest() {
        return statistic.getTop5Quickest();
    }

}
