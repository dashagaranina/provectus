package com.provectus.task2;

import com.provectus.task2.client.wrapper.WorkerWrapper;
import com.provectus.task2.model.Result;
import com.provectus.task2.repository.ResultRepository;
import com.provectus.task2.service.impl.DistributedSolution;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

//@RunWith(SpringRunner.class)
@SpringBootTest(classes = Task2ApplicationTests.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class SolutionTest {

    @InjectMocks
    private DistributedSolution solution;

    @Autowired
    private ResultRepository repository;

    @Mock
    private WorkerWrapper workerWrapper;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test(){
        Mockito.when(workerWrapper.wrap(1, 2)).thenReturn(CompletableFuture.completedFuture(BigDecimal.valueOf(3.13)));
        Integer accuracy = 2;
        Integer id =solution.leibniz(accuracy);
        Assert.assertNotNull(id);
        Result result = solution.getResult(id);
        Assert.assertNotNull(result);
        System.out.println("========"+result.toString());
        Assert.assertNotNull(result.getResult());
    }

    @Profile("test")
    @Configuration
    static class TestConfiguration {
        @Bean
        @Primary
        public WorkerWrapper workerWrapper() {
            return Mockito.mock(WorkerWrapper.class);
        }
    }
}
