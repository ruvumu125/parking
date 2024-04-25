package com.parking.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.parking.model.Transaction;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "select t from Transaction t order by t.id desc")
    Page<Transaction> findAllTransaction(Pageable pageable);

    List<Transaction> findByAccountId(Long accountId);

    @Query("select sum(t.transactionAmount) from  Transaction t where t.account.id= :accountId")
    BigDecimal accountSold(@Param("accountId") Long accountId);
}
