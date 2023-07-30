package com.spring.batch.springbatch.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountSearchDTO {
    private String accountNumber;
    private String customerId;
    private String description;
}
