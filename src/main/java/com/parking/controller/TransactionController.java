package com.parking.controller;

import java.util.List;

import com.parking.dto.DepositDto;
import com.parking.dto.PaymentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.parking.controller.api.TransactionApi;
import com.parking.dto.TransactionDto;
import com.parking.services.TransactionService;

@RestController
public class TransactionController implements TransactionApi {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public PaymentDto savePayment(PaymentDto dto) {
        return transactionService.savePayment(dto);
    }

    @Override
    public DepositDto saveDeposit(DepositDto dto) {
        return transactionService.saveDeposit(dto);
    }
}
