package com.provectus.task2;

import com.provectus.task2.client.wrapper.WorkerWrapper;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AbstractTest.TestConfiguration.class)
public abstract class AbstractTest {

    @Configuration
    @EnableAutoConfiguration
    @ComponentScan(basePackages = "com.provectus.task2.service")
    @EnableJpaRepositories(basePackages = "com.provectus.task2.repository")
    @EntityScan(basePackages = "com.provectus.task2.model")
    @Transactional
    @Rollback
    static class TestConfiguration {

        @Bean
        @Primary
        public WorkerWrapper workerWrapper() {
            return Mockito.mock(WorkerWrapper.class);
        }

    }

}
