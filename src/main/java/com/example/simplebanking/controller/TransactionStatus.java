package com.example.simplebanking.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TransactionStatus {

    private String status;
    private String approvalCode;
    private LocalDateTime date;
    private String type;
}
