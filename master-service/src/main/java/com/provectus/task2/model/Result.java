package com.provectus.task2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    @Id
    private Integer id;

    private String result;

    private Integer accuracy;

    private Integer timeSpend;

}
