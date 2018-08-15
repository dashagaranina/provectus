package com.provectus.task2;

import com.provectus.task2.model.Result;
import com.provectus.task2.repository.ResultRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResultRepositoryTest {

    @Autowired
    private ResultRepository repository;

    @Test
    public void test(){
        Result result = new Result("test", 1, 1);
        Result save = repository.save(result);
        Assert.assertNotNull(save);
        Assert.assertEquals(result.getResult(), save.getResult());

        result = repository.getOne(save.getId());
        Assert.assertNotNull(result);

    }
}
