package com.parking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.parking.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "select a from Account a order by a.id desc")
    Page<Account> findAllAccount(Pageable pageable);
}
