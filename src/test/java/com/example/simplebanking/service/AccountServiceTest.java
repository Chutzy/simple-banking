package com.example.simplebanking.service;

import com.example.simplebanking.controller.TransactionStatus;
import com.example.simplebanking.model.*;
import com.example.simplebanking.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.simplebanking.constants.Constants.OK;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    private final Account account = new Account();
    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        account.setAccountNumber("1234");
        account.setBalance(500);
        account.setOwner("test");
        account.setCreateDate(LocalDateTime.now());
    }

    @Test
    public void depositTest_Successful() throws InsufficientBalanceException {
        when(accountRepository.findByAccountNumber(any())).thenReturn(Optional.of(account));
        TransactionStatus transactionStatus = accountService.deposit("1234", 200);
        Assertions.assertEquals(OK, transactionStatus.getStatus());
    }

    @Test
    public void withdrawTest_Success() {
        when(accountRepository.findByAccountNumber(any())).thenReturn(Optional.of(account));
        Assertions.assertDoesNotThrow(() -> accountService.withdraw("1234", 200));
    }

    @Test
    public void payBill_Success() {
        when(accountRepository.findByAccountNumber(any())).thenReturn(Optional.of(account));
        Assertions.assertDoesNotThrow(() -> accountService.payBill("1234", 200));
    }

    @Test
    public void depositTest_Failed() {
        when(accountRepository.findByAccountNumber(any())).thenReturn(Optional.of(account));
        Assertions.assertThrows(InsufficientBalanceException.class, () -> accountService.deposit("1234", 0));
    }

    @Test
    public void withdrawTest_Failed() {
        account.setBalance(100);
        when(accountRepository.findByAccountNumber(any())).thenReturn(Optional.of(account));
        Assertions.assertThrows(InsufficientBalanceException.class, () -> accountService.withdraw("1234", 300));
    }

    @Test
    public void modelTest() {
        var withdrawalTransaction = new WithdrawalTransaction(200.0);
        Assertions.assertEquals(200, withdrawalTransaction.getAmount());

        var depositTransaction = new DepositTransaction(200.0);
        Assertions.assertEquals(200, depositTransaction.getAmount());

        var billPaymentTransaction = new BillPaymentTransaction(200.0, "test");
        Assertions.assertEquals("test", billPaymentTransaction.getPayee());

        Assertions.assertEquals("BillPaymentTransaction", billPaymentTransaction.toString().substring(0, 22));
    }
}
