package com.provectus.task2;

import com.provectus.task2.client.wrapper.WorkerWrapper;
import com.provectus.task2.model.Result;
import com.provectus.task2.service.impl.DistributedSolution;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

public class DistributedSolutionTest extends AbstractTest {

    @Autowired
    private DistributedSolution solution;

    @Autowired
    private WorkerWrapper workerWrapper;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLeibniz() throws InterruptedException {
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
}