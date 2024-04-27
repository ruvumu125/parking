package com.parking.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction extends AbstractEntity {

    @Column(name = "transactioncode")
    private String transactionCode;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private VehiculeAccount account;

    @Enumerated(EnumType.STRING)
    private TransactionTypeEnum transactionType;

    @Column(name = "transactionamount")
    private BigDecimal transactionAmount;

    @Column(name = "transactiondate",updatable = false)
    private Instant transactionDate;

    @OneToMany(mappedBy = "transaction")
    private List<Payment> payments;

    @OneToMany(mappedBy = "transaction")
    private List<Deposit> deposits;
}
