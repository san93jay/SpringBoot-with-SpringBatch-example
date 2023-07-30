package com.spring.batch.springbatch.security.batchConfig;

import com.spring.batch.springbatch.security.model.Account;
import com.spring.batch.springbatch.security.repository.AccountRepository;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;


@Configuration
public class AccountBatchConfig {
    @Autowired
    private AccountRepository accountRepository;
    
@Bean
public FlatFileItemReader<Account> reader() {

    DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
    tokenizer.setDelimiter("|");
    tokenizer.setStrict(false);
    tokenizer.setNames(new String[]{"ACCOUNT_NUMBER", "TRX_AMOUNT", "DESCRIPTION", "TRX_DATE", "TRX_TIME", "CUSTOMER_ID"});

    DefaultLineMapper lineMapper = new DefaultLineMapper<Account>();
    lineMapper.setLineTokenizer(tokenizer);


    return new FlatFileItemReaderBuilder<Account>()
            .name("accountItemReader")
            .resource(new FileSystemResource("src/main/resources/dataSource.txt")).strict(false)
            .linesToSkip(1)
            .lineTokenizer(tokenizer)
            .fieldSetMapper(new BeanWrapperFieldSetMapper<Account>() {{
                setTargetType(Account.class);
            }})
            .build();
}

   @Bean
   public AccountBatchProcessor accountProcessor(){
    return  new AccountBatchProcessor();
   }

   @Bean
   public RepositoryItemWriter<Account> writer(){
       RepositoryItemWriter<Account> writer=new RepositoryItemWriter<>();
       writer.setRepository(accountRepository);
       writer.setMethodName("save");
       return writer;
   }

}
