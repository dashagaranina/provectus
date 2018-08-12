package com.provectus.task2.service;

import com.provectus.task2.model.Result;

import java.util.List;

public interface StatisticService {

    Integer count ();
    List<Result> getAllByAccuracy(Integer accuracy);
    List<Result> getAll();
    Result getResult (Integer id);


}
