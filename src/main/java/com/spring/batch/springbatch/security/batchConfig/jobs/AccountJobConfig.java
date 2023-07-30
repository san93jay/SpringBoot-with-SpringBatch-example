package com.spring.batch.springbatch.security.batchConfig.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AccountJobConfig {

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   Step step) {
        return jobBuilderFactory
                .get("job")
                .flow(step)
                .end()
                .build();
    }
}
