package com.example.money_transfer_service.exception;

public class BankErrorInputData extends RuntimeException{
    public BankErrorInputData(String message) {
        super(message);
    }
}
