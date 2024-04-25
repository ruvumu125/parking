package com.parking.services;

import com.parking.dto.DepositDto;
import com.parking.dto.PaymentDto;

public interface TransactionService {

    PaymentDto savePayment(PaymentDto dto);
    DepositDto saveDeposit(DepositDto dto);
}
