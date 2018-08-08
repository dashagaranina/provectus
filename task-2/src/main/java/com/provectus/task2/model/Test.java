package com.provectus.task2.model;

public class Test {
    private long id;
    private String result;
    private Long timeSpend;

    public Test(long id, String result, Long timeSpend) {
        this.id = id;
        this.result = result;
        this.timeSpend = timeSpend;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getTimeSpend() {
        return timeSpend;
    }

    public void setTimeSpend(Long timeSpend) {
        this.timeSpend = timeSpend;
    }
}
