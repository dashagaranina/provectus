package com.provectus.task2.service.impl;

import com.provectus.task2.model.Result;
import com.provectus.task2.repository.ResultRepository;
import com.provectus.task2.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

    private final ResultRepository repository;


    @Override
    public Integer count() {
        return repository.findAll().size();
    }

    @Override
    public List<Result> getAllByAccuracy(Integer accuracy) {
        return repository.findByAccuracy(accuracy);
    }

    @Override
    public List<Result> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Result> getTop5Slowest(){
        return repository.findTop5ByOrderByTimeSpendDesc();
    }

    @Override
    public List<Result> getTop5Quickest() {
        return repository.findTop5ByOrderByTimeSpend();

    }

}
