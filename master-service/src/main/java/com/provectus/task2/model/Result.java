package com.provectus.task2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Result {

    @Id
    @GeneratedValue
    private Integer id;

    private String result;

    private Integer accuracy;

    private Integer timeSpend;

    public Result (){}

    public Result(String result, Integer accuracy, Integer timeSpend) {

        this.result = result;

        this.accuracy = accuracy;

        this.timeSpend = timeSpend;

    }

}
