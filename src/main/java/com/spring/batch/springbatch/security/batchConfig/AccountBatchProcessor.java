package com.spring.batch.springbatch.security.batchConfig;

import com.spring.batch.springbatch.security.model.Account;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;


@Log4j2
public class AccountBatchProcessor implements ItemProcessor<Account, Account> {

    @Override
    public Account process(Account account) throws Exception {
        return account;
    }
}
