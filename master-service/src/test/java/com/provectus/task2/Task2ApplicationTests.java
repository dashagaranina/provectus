package com.provectus.task2;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@PropertySource("application.yml")
public class Task2ApplicationTests{

    @Test
    public void test(){
        Assert.assertTrue(true);
    }


    @Test
    public void contextLoads() {
    }

}
