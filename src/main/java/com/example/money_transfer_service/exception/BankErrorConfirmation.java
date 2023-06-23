package com.example.money_transfer_service.exception;


public class BankErrorConfirmation extends RuntimeException{
    public BankErrorConfirmation(String message) {
        super(message);
    }
}
