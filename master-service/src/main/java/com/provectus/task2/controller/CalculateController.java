package com.provectus.task2.controller;

import com.provectus.task2.model.Result;
import com.provectus.task2.service.Solution;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(description = "Pi calculation interface")
public class CalculateController {

    private final Solution solution;

    @GetMapping(value = "/pi")
    @ApiOperation("Calculates PI for accurancy from params. Returns result ID")
    public Integer pi(@RequestParam(value = "accuracy", defaultValue = "5") Integer accuracy) {
        return solution.leibniz(accuracy);
    }


    @GetMapping("/result/{id}")
    @ApiOperation("Returns result of Pi calculation by ID")
    public Result result(@PathVariable Integer id) {
        return solution.getResult(id);
    }

}
