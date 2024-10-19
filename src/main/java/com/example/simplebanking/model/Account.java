package com.example.simplebanking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.simplebanking.constants.Constants.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String accountNumber;

    private String owner;

    private double balance;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "accountNumber", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();

    public void debit(double amount) throws InsufficientBalanceException {
        validation(amount, TRANSACTION_TYPE_DEBIT);
        Transaction withdrawalTransaction = new WithdrawalTransaction(amount);
        withdrawalTransaction.setAccountNumber(this.accountNumber);
        this.balance = balance - amount;
        post(withdrawalTransaction);
    }

    public void credit(double amount) throws InsufficientBalanceException {
        validation(amount, TRANSACTION_TYPE_CREDIT);
        Transaction depositTransaction = new DepositTransaction(amount);
        depositTransaction.setAccountNumber(this.accountNumber);
        this.balance = balance + amount;
        post(depositTransaction);
    }

    private void validation(double amount, String transactionType) throws InsufficientBalanceException {
        if (amount == 0) {
            throw new InsufficientBalanceException(ZERO_AMOUNT);
        }
        if (TRANSACTION_TYPE_DEBIT.equals(transactionType) && (balance < amount)) {
            throw new InsufficientBalanceException(INSUFFICIENT_BALANCE);
        }
    }

    public void post(Transaction transaction) {
        this.transactions.add(transaction);
    }

}
