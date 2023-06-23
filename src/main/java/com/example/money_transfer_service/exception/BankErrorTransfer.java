package com.example.money_transfer_service.exception;

public class BankErrorTransfer extends RuntimeException {
    public BankErrorTransfer(String message) {
        super(message);
    }
}
