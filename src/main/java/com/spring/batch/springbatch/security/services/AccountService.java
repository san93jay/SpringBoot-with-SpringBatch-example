package com.spring.batch.springbatch.security.services;

import com.spring.batch.springbatch.security.dto.AccountDTO;
import com.spring.batch.springbatch.security.dto.AccountSearchDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
    List<AccountDTO> searchAccountDetails(AccountSearchDTO accountSearchDTO, Pageable pageable);
    public AccountDTO updateAccountDetails(AccountDTO accountDTO);
}
