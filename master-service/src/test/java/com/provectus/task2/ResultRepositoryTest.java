package com.provectus.task2;

import com.provectus.task2.model.Result;
import com.provectus.task2.repository.ResultRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ResultRepositoryTest extends AbstractTest {

    @Autowired
    private ResultRepository repository;

    @Test
    public void testJpa(){
        Result result = new Result("test", 1, 1);
        Result save = repository.save(result);
        Assert.assertNotNull(save);
        Assert.assertEquals(result.getResult(), save.getResult());

        result = repository.getOne(save.getId());
        Assert.assertNotNull(result);

    }
}
