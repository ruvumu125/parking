package com.parking.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.parking.dto.PaymentDto;

public class PaymentValidator {

    public static List<String> validate(PaymentDto paymentDto){
        List<String> errors = new ArrayList<>();

        if(paymentDto == null) {
            errors.add("Veuiller selectionner un ticket");
            errors.add("Veuillez selectionner un compte");
            errors.add("Veuiller renseigner le montant de transaction");
            errors.add("Le montant de transaction doit etre superieur à zero");
            return errors;
        }

        if(paymentDto.getParkingTicket() == null || paymentDto.getParkingTicket().getId() == null || paymentDto.getParkingTicket().getId().compareTo(0L) == 0) {
            errors.add("Veuiller selectionner un ticket");
        }
        if(paymentDto.getTransaction().getAccount() == null || paymentDto.getTransaction().getAccount().getId() == null || paymentDto.getTransaction().getAccount().getId().compareTo(0L) == 0) {
            errors.add("Veuillez selectionner un compte");
        }

        if(paymentDto.getTransaction().getTransactionAmount() == null) {
            errors.add("Veuiller renseigner le montant de transaction");
        }
        if(paymentDto.getTransaction().getTransactionAmount() == null || paymentDto.getTransaction().getTransactionAmount().compareTo(BigDecimal.valueOf(0)) == 0) {
            errors.add("Le montant de transaction doit etre superieur à zero");
        }

        return errors;
    }
}
