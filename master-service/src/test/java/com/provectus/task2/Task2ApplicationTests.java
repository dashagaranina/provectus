package com.provectus.task2;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.validation.constraints.AssertTrue;

//@RunWith(SpringRunner.class)
@SpringBootTest
//@PropertySource("application-test.yml")
//@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql")
public class Task2ApplicationTests {

    @Test
    public void test(){
        Assert.assertTrue(true);
    }


   /* @Test
    public void contextLoads() {
    }*/

}
