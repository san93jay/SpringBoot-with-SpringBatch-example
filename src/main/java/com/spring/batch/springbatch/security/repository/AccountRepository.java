package com.spring.batch.springbatch.security.repository;

import com.spring.batch.springbatch.security.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Page<Account> findByAccountNumberOrCustomerIdOrDescription(@Param("accountNumber") String accountNumber,
                                                               @Param("customerId") String customerId,
                                                               @Param("description") String description,
                                                               Pageable pageRequest);


    Page<Account> findByAccountNumberContainingOrCustomerIdContainingOrDescriptionContaining(@Param("accountNumber") String accountNumber,
                                                                                             @Param("customerId") String customerId,
                                                                                             @Param("description") String description,
                                                                                             Pageable pageRequest);
}
