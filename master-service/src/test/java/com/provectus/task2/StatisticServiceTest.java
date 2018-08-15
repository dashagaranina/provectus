package com.provectus.task2;

import com.provectus.task2.model.Result;
import com.provectus.task2.repository.ResultRepository;
import com.provectus.task2.service.StatisticService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = "com.provectus.task2.service")
@EnableJpaRepositories(basePackages = "com.provectus.task2.repository")
@EntityScan(basePackages = "com.provectus.task2.model")
public class StatisticServiceTest {

    private static final Result RESULT_1 = new Result("3.14", 2, 100);
    private static final Result RESULT_2 = new Result("3.14", 2, 200);
    private static final Result RESULT_3 = new Result("3.14", 2, 300);
    private static final Result RESULT_4 = new Result("3.141", 3, 400);
    private static final Result RESULT_5 = new Result("3.141", 3, 500);
    private static final Result RESULT_6 = new Result("3.14159", 5, 600);

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private ResultRepository repository;

    @Before
    public void before() {
        repository.save(RESULT_1);
        repository.save(RESULT_2);
        repository.save(RESULT_3);
        repository.save(RESULT_4);
        repository.save(RESULT_5);
        repository.save(RESULT_6);
    }

    @Test
    public void testCount() {
        Integer count = statisticService.count();
        Assert.assertNotNull(count);
        Assert.assertTrue(count == 6);
    }

    @Test
    public void testGetAllByAccuracy() {
        List<Result> allByAccuracy = statisticService.getAllByAccuracy(2);
        Assert.assertNotNull(allByAccuracy);
        Assert.assertTrue(allByAccuracy.get(0).getAccuracy() == 2);
        Assert.assertEquals(allByAccuracy.size(), 3);
    }

    @Test
    public void testGetAll() {
        List<Result> all = statisticService.getAll();
        Assert.assertNotNull(all);
        Assert.assertEquals(all.size(), 6);
    }

    @Test
    public void testGetTop5Slowest() {
        List<Result> top5Slowest = statisticService.getTop5Slowest();
        Assert.assertNotNull(top5Slowest);
        Assert.assertEquals(top5Slowest.size(), 5);
        Assert.assertEquals(top5Slowest.get(0), RESULT_6);
    }
    @Test
    public void testGetTop5Quickest() {
        List<Result> top5Quickest = statisticService.getTop5Quickest();
        Assert.assertNotNull(top5Quickest);
        Assert.assertEquals(top5Quickest.size(), 5);
        Assert.assertEquals(top5Quickest.get(0), RESULT_1);
    }

}
