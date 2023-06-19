package com.example.money_transfer_service.model;

public record RequestTransfer(String cardFromNumber,
                              String cardFromValidTill,
                              String cardFromCVV,
                              String cardToNumber,
                              Amount amount) {
}
