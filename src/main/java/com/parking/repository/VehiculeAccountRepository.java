package com.parking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.parking.model.VehiculeAccount;

public interface VehiculeAccountRepository extends JpaRepository<VehiculeAccount, Long> {

    @Query(value = "select a from VehiculeAccount a order by a.id desc")
    Page<VehiculeAccount> findAllAccount(Pageable pageable);
}
