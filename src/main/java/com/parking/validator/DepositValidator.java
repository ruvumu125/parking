package com.parking.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.parking.dto.DepositDto;

public class DepositValidator {

    public static List<String> validate(DepositDto depositDto){
        List<String> errors = new ArrayList<>();

        if(depositDto == null) {
            errors.add("Veuillez selectionner un compte");
            errors.add("Veuiller renseigner le montant de transaction");
            errors.add("Le montant de transaction doit etre superieur à zero");
            return errors;
        }
        if(depositDto.getTransaction().getAccount() == null || depositDto.getTransaction().getAccount().getId() == null || depositDto.getTransaction().getAccount().getId().compareTo(0L) == 0) {
            errors.add("Veuillez selectionner un compte");
        }

        if(depositDto.getTransaction().getTransactionAmount() == null) {
            errors.add("Veuiller renseigner le montant de transaction");
        }
        if(depositDto.getTransaction().getTransactionAmount() == null || depositDto.getTransaction().getTransactionAmount().compareTo(BigDecimal.valueOf(0)) == 0) {
            errors.add("Le montant de transaction doit etre superieur à zero");
        }

        return errors;
    }
}
