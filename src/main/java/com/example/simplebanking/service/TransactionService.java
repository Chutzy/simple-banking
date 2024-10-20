package com.example.simplebanking.service;

import com.example.simplebanking.model.Transaction;
import com.example.simplebanking.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    TransactionRepository transactionRepository;

    public List<Transaction> getTransactions(String accountNumber){
        return transactionRepository.findAllByAccountNumber(accountNumber);
    }
}
