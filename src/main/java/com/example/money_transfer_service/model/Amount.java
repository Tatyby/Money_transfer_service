package com.example.money_transfer_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Amount {
    private final int value;
    private final String currency;

}
