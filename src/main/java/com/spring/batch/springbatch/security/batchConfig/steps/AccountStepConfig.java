package com.spring.batch.springbatch.security.batchConfig.steps;

import com.spring.batch.springbatch.security.model.Account;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
public class AccountStepConfig {

    @Value("${max.chunk}")
    private int maxChunk;

    @Bean
    public Step step(StepBuilderFactory stepBuilderFactory,
                     ItemReader<Account> accountItemReader,
                     ItemProcessor<Account, Account> accountItemProcessor,
                     ItemWriter<Account> accountItemWriter) {
        return stepBuilderFactory
                .get("AccountStep")
                .<Account, Account>chunk(maxChunk)
                .reader(accountItemReader)
                .processor(accountItemProcessor)
                .writer(accountItemWriter)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor=new SimpleAsyncTaskExecutor();
        simpleAsyncTaskExecutor.setConcurrencyLimit(10);
        return simpleAsyncTaskExecutor;
    }
}
