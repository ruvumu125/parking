package com.parking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.parking.model.Deposit;

public interface DepositRepository extends JpaRepository<Deposit, Long> {

    @Query(value = "select d from Deposit d order by d.id desc")
    Page<Deposit> findAllDeposit(Pageable pageable);
}
