package com.spring.batch.springbatch.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {
    private int id;
    private String accountNumber;
    private float trxAmount;
    private String description;
    private String trxDate;
    private String trxTime;
    private String customerId;
}
