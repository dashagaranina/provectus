package com.provectus.worker;

import com.provectus.worker.service.Solution;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkerApplicationTests {

	@Autowired
	private Solution solution;

	@Test
	public void testCalculation() {
		BigDecimal bigDecimal = solution.leibnizPi(1, 212).setScale(2, RoundingMode.DOWN);
		Assert.assertNotNull(bigDecimal);
		Assert.assertEquals(bigDecimal, BigDecimal.valueOf(3.13));
	}

}
