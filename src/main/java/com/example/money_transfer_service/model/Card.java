package com.example.money_transfer_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Card {
    private String cardNumber;
    private String cardValidTill;
    private String carCVV;
    private Amount balance;
}
