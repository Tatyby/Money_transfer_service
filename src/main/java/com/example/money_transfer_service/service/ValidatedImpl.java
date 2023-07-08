package com.example.money_transfer_service.service;

import com.example.money_transfer_service.exception.BankErrorInputData;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.Year;

@Slf4j
public class ValidatedImpl implements Validated {
    private final String CARD_NUMBER_16_DIGITS = "Номер карты должен состоять из 16 цифр";
    private final String ENTER_DATA_CARD = "Введите срок действия карты";
    private final String ENTER_CORRECT_MONTH = "Введите корректный месяц";
    private final String CARD_EXPIRED_WRONG_YEAR = "Истек срок действия карты, неверный год";
    private final String CARD_EXPIRED_WRONG_MONTH = "Истек срок действия карты, неверный месяц";
    private final String ENTER_CORRECT_CVV = "Введите корректный CVV код";
    private final String AMOUNT_OPERATION_MORE_THEN_ZERO = "Сумма операции должна быть больше нуля";

    @Override
    public void validatedNumberCard(String cardNumber) {
        if (cardNumber.length() != 16) {
            log.info(CARD_NUMBER_16_DIGITS);
            throw new BankErrorInputData(CARD_NUMBER_16_DIGITS);
        }
    }

    @Override
    public void validatedDataCard(String cardValidTill) {
        if (cardValidTill == null) {
            throw new BankErrorInputData(ENTER_DATA_CARD);
        }
        String[] cardValidTillYearAndMonth = cardValidTill.split("/");
        int month = Integer.parseInt(cardValidTillYearAndMonth[0]);
        int year = Integer.parseInt(cardValidTillYearAndMonth[1]) + 2000;
        if (month > 12 || month < 1) {
            throw new BankErrorInputData(ENTER_CORRECT_MONTH);
        }
        if (year < Year.now().getValue()) {
            throw new BankErrorInputData(CARD_EXPIRED_WRONG_YEAR);
        }
        if (year == Year.now().getValue() && month < LocalDate.now().getMonthValue()) {
            throw new BankErrorInputData(CARD_EXPIRED_WRONG_MONTH);
        }
    }

    @Override
    public void validatedCVVCard(String cardCVV) {
        if (cardCVV == null || cardCVV.length() != 3 || !cardCVV.chars().allMatch(Character::isDigit)) {
            throw new BankErrorInputData(ENTER_CORRECT_CVV);
        }
    }

    @Override
    public void validatedAmountCard(int amount) {
        if (amount <= 0) {
            throw new BankErrorInputData(AMOUNT_OPERATION_MORE_THEN_ZERO);
        }
    }
}
