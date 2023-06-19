package com.example.money_transfer_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Card {
    private final String cardNumber;
    private final String cardValidTill;
    private final String carCVV;

}
