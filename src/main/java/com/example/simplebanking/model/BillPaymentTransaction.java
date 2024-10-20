package com.example.simplebanking.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillPaymentTransaction extends Transaction {

    private String payee;

    public BillPaymentTransaction(double amount, String payee) {
        super(amount);
        this.payee = payee;
    }

}
