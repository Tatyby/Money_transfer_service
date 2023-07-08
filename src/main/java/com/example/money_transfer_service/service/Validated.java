package com.example.money_transfer_service.service;

public interface Validated {
    void validatedNumberCard(String cardNumber);

    void validatedDataCard(String cardValidTill);

    void validatedCVVCard(String cardCVV);

    void validatedAmountCard(int amount);
}
