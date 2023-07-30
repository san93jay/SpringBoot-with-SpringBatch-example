package com.spring.batch.springbatch.security.serviceImpl;

import com.spring.batch.springbatch.security.dto.AccountDTO;
import com.spring.batch.springbatch.security.dto.AccountSearchDTO;
import com.spring.batch.springbatch.security.model.Account;
import com.spring.batch.springbatch.security.repository.AccountRepository;
import com.spring.batch.springbatch.security.services.AccountService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
@Log4j2
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;
    private final ReentrantLock fairLock=new ReentrantLock(true);



    @Override
    public List<AccountDTO> searchAccountDetails(AccountSearchDTO accountSearchDTO, Pageable pageable) {
        Page<Account> accountDTOS=null;
        List<AccountDTO> accountDTOList=null;
        try{
            accountDTOS= accountRepository.findByAccountNumberContainingOrCustomerIdContainingOrDescriptionContaining
                    (accountSearchDTO.getAccountNumber(),accountSearchDTO.getCustomerId(), accountSearchDTO.getDescription(),pageable);
            if(!accountDTOS.isEmpty()){
                List<Account> accountList=accountDTOS.getContent();
                accountDTOList = accountList
                                .stream()
                                .map(account -> modelMapper.map(account, AccountDTO.class))
                                .collect(Collectors.toList());
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }

        return accountDTOList;
    }

    @Override
    public AccountDTO updateAccountDetails(AccountDTO accountDTOs) {
        AccountDTO accountDTO=new AccountDTO();
        boolean isLockAcquired = false;
        try {
            isLockAcquired = fairLock.tryLock(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
        if (isLockAcquired) {
            try {
                Optional<Account>  accountDB= accountRepository.findById(accountDTOs.getId());
                if(accountDB!=null){
                    Account account=modelMapper.map(accountDTOs, Account.class);
                    accountRepository.save(account);
                    log.info("update successfully");
                    accountDTO=modelMapper.map(accountDB,AccountDTO.class);
                }
                // heavy operation
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            } finally {
                fairLock.unlock();
            }
        }
        return accountDTO;
    }
}
