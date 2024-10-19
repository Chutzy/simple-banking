package com.example.simplebanking.service;

import com.example.simplebanking.controller.TransactionStatus;
import com.example.simplebanking.model.Account;
import com.example.simplebanking.model.InsufficientBalanceException;
import com.example.simplebanking.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.simplebanking.constants.Constants.*;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public TransactionStatus deposit(String accountNumber, double amount) throws InsufficientBalanceException {
        Account account = getAccount(accountNumber);
        account.credit(amount);
        accountRepository.save(account);
        return new TransactionStatus(OK, UUID.randomUUID().toString(), LocalDateTime.now(), DEPOSIT_TRANSACTION);
    }

    public TransactionStatus withdraw(String accountNumber, double amount) throws InsufficientBalanceException {
        Account account = getAccount(accountNumber);
        account.debit(amount);
        accountRepository.save(account);
        return new TransactionStatus(OK, UUID.randomUUID().toString(), LocalDateTime.now(), WITHDRAW_TRANSACTION);
    }

    public Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public TransactionStatus payBill(String payee, double amount) throws InsufficientBalanceException {
        Account account = getAccount(payee);
        account.debit(amount);
        accountRepository.save(account);
        return new TransactionStatus(OK, UUID.randomUUID().toString(),  LocalDateTime.now(), BILL_PAYMENT);
    }
}
