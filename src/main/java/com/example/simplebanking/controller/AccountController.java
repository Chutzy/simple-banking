package com.example.simplebanking.controller;

import com.example.simplebanking.model.Account;
import com.example.simplebanking.model.InsufficientBalanceException;
import com.example.simplebanking.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account/v1")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionStatus> credit(@PathVariable("accountNumber") String accountNumber, @RequestBody double amount) throws InsufficientBalanceException {
        TransactionStatus transactionStatus = accountService.deposit(accountNumber, amount);
        return ResponseEntity.ok(transactionStatus);
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionStatus> debit(@PathVariable("accountNumber") String accountNumber, @RequestBody double amount) throws InsufficientBalanceException {
        TransactionStatus transactionStatus = accountService.withdraw(accountNumber, amount);
        return ResponseEntity.ok(transactionStatus);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable("accountNumber") String accountNumber) {
        Account account = accountService.getAccount(accountNumber);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/billPayment/{accountNumber}")
    public ResponseEntity<TransactionStatus> billPayment(@PathVariable("accountNumber") String accountNumber, @RequestBody double amount) throws InsufficientBalanceException {
        TransactionStatus transactionStatus = accountService.payBill(accountNumber, amount);
        return ResponseEntity.ok(transactionStatus);
    }

}
