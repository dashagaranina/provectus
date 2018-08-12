package com.provectus.task2.repository;

import com.provectus.task2.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository  extends JpaRepository<Result, Integer> {

    List<Result> findByAccuracy (Integer accuracy);
}
