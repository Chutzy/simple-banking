package com.example.simplebanking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;

    private LocalDateTime date = LocalDateTime.now();

    private double amount;

    public Transaction(double amount) {
        this.amount = amount;
    }

    public Transaction() {
    }
}
