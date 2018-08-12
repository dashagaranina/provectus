package com.provectus.task2.controller;

import com.provectus.task2.model.Result;
import com.provectus.task2.service.Solution;
import com.provectus.task2.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
//@Api(description = "Blah blah.")
public class CalculateController {

    private final Solution solution;
    private final StatisticService statistic;

    @GetMapping(value = "/pi")
//    @ApiOperation("${calculatecontroller.pi}")
    public Integer pi (@RequestParam(value = "accuracy", defaultValue = "5") Integer accuracy) {
        return solution.leibniz(accuracy);
    }


    @GetMapping("/result/{id}")
    public Result result(@PathVariable Integer id) {
        return statistic.getResult(id);
    }

    @GetMapping("/statistic/count")
    public Integer count () {
        return statistic.count();
    }

    @GetMapping("/statistic/accuracy/{accuracy}")
    public List<Result> count(@PathVariable("accuracy") Integer accuracy) {
        return statistic.getAllByAccuracy(accuracy);
    }

    @GetMapping("statistic/result")
    public List<Result> resultsAll () {
        return statistic.getAll();
    }


}
