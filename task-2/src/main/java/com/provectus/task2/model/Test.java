package com.provectus.task2.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Test {
    private long id;
    private String result;
    private Long timeSpend;
}
