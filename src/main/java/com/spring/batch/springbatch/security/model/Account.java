package com.spring.batch.springbatch.security.model;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Account")
public class Account implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @Column(name = "TRX_AMOUNT")
    private float trxAmount;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TRX_DATE")
    private String trxDate;

    @Column(name = "TRX_TIME")
    private String trxTime;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "customer_id", nullable = false)
//    private Customer customer;

    @Column(name = "CUSTOMER_ID")
    private String customerId;

}
