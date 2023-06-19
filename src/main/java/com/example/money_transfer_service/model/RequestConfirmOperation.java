package com.example.money_transfer_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestConfirmOperation {
    private final String  operationId;
    private final String code;
}
