package com.provectus.task2;

import com.provectus.task2.client.wrapper.WorkerWrapper;
import com.provectus.task2.model.Result;
import com.provectus.task2.service.impl.DistributedSolution;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@SpringBootTest(classes = DistributedSolutionTest.TestConfiguration.class)
@RunWith(SpringRunner.class)
public class DistributedSolutionTest {

    @Autowired
    private DistributedSolution solution;

    @Autowired
    private WorkerWrapper workerWrapper;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() throws InterruptedException {
        Mockito.when(workerWrapper.wrap(1, 70)).thenReturn(CompletableFuture.completedFuture(BigDecimal.valueOf(3.127)));
        Mockito.when(workerWrapper.wrap(71, 140)).thenReturn(CompletableFuture.completedFuture(BigDecimal.valueOf(0.007)));
        Mockito.when(workerWrapper.wrap(141, 210)).thenReturn(CompletableFuture.completedFuture(BigDecimal.valueOf(0.002)));

        Integer accuracy = 2;
        Integer id = solution.leibniz(accuracy);

        Assert.assertNotNull(id);

        Thread.sleep(5000);
        Result result = solution.getResult(id);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getResult());
        Assert.assertEquals(result.getResult(), "3.14");
    }

    @Configuration
    @EnableAutoConfiguration
    @ComponentScan(basePackages = "com.provectus.task2.service")
    @EnableJpaRepositories(basePackages = "com.provectus.task2.repository")
    @EntityScan(basePackages = "com.provectus.task2.model")
    static class TestConfiguration {

        @Bean
        @Primary
        public WorkerWrapper workerWrapper() {
            return Mockito.mock(WorkerWrapper.class);
        }

    }
}