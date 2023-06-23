package com.example.money_transfer_service.service;

import com.example.money_transfer_service.exception.BankErrorConfirmation;
import com.example.money_transfer_service.exception.BankErrorInputData;
import com.example.money_transfer_service.exception.BankErrorTransfer;
import com.example.money_transfer_service.model.RequestConfirmOperation;
import com.example.money_transfer_service.model.RequestTransfer;
import com.example.money_transfer_service.model.ResponsMoney;
import com.example.money_transfer_service.repository.BankRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;

@Service
@AllArgsConstructor
@Slf4j
public class BankService {
    private final BankRepository bankRepository;
    private final String NO_MONEY = "Недостаточно средств на карте";
    private final String INVALID_CODE = "Не верный код";
    private final String staticCode = "0000";
    private final String CARD_NUMBER_16_DIGITS = "Номер карты должен состоять из 16 цифр";
    private final String ENTER_DATA_CARD = "Введите срок действия карты";
    private final String ENTER_CORRECT_MONTH = "Введите корректный месяц";
    private final String CARD_EXPIRED_WRONG_YEAR = "Истек срок действия карты, неверный год";
    private final String CARD_EXPIRED_WRONG_MONTH = "Истек срок действия карты, неверный месяц";
    private final String ENTER_CORRECT_CVV = "Введите корректный CVV код";
    private final String AMOUNT_OPERATION_MORE_THEN_ZERO = "Сумма операции должна быть больше нуля";

    public ResponsMoney transfer(RequestTransfer requestTransfer) { //возвращает id операции

        final String cardFromNumber = requestTransfer.cardFromNumber();
        final String cardFromValidTill = requestTransfer.cardFromValidTill();
        final String cardFromCVV = requestTransfer.cardFromCVV();
        final String cardToNumber = requestTransfer.cardToNumber();
        final int amount = requestTransfer.amount().value();
        final int commission = amount / 100;

        validatedNumberCard(cardFromNumber);
        validatedNumberCard(cardToNumber);
        validatedCVVCard(cardFromCVV);
        validatedDataCard(cardFromValidTill);
        validatedAmountCard(amount);

        if (check(commission, amount)) {
            final String idOperation = String.valueOf(bankRepository.getId());
            int balanceFromCard = bankRepository.getAmountFromValue() - amount - commission;
            int balanceToCard = bankRepository.getAmountToValue() + amount + commission;

            int code = (int) (Math.random() * 1000 - 9999);
            bankRepository.saveBalanceFromCard(idOperation, balanceFromCard);
            bankRepository.saveBalanceToCard(idOperation, balanceToCard);
            bankRepository.saveNumberFromCard(idOperation, cardFromNumber);
            bankRepository.saveNumberToCard(idOperation, cardToNumber);


            log.info("Перевод c карты {} на карту {} в размере {}, с комиссией {} отправлен код подтверждения{}",
                    cardFromNumber, cardToNumber, amount, commission, code);
            return new ResponsMoney(idOperation);
        } else {
            log.info(NO_MONEY);
            throw new BankErrorTransfer(NO_MONEY); //500 ошибка
        }
    }

    public boolean check(int commission, int amount) { //метод проверяющий хватит ли средств на карте
        int balanceFromCard = bankRepository.getAmountFromValue();
        if ((amount + commission) <= balanceFromCard) {
            return true;
        } else {
            return false;
        }
    }

    public ResponsMoney confirmOperation(RequestConfirmOperation requestConfirmOperation) { // тут проверяется валидация смс кода
        final String idOperation = requestConfirmOperation.getOperationId();
        final String code = requestConfirmOperation.getCode();
        if (code.equals(bankRepository.getCode(idOperation)) || code.equals(staticCode)) {
            log.info("Перевод выполнен успешно, баланс карты {}  равен {}, баланс карты {} равен {} ",
                    bankRepository.getNumberFromCard(idOperation), bankRepository.getBalanceFromCard(idOperation),
                    bankRepository.getNumberToCard(idOperation), bankRepository.getBalanceToCard(idOperation));
            return new ResponsMoney(idOperation);
        } else {
            throw new BankErrorConfirmation(INVALID_CODE);
        }
    }

    public void validatedNumberCard(String cardNumber) {
        if (cardNumber.length() != 16) {
            log.info(CARD_NUMBER_16_DIGITS);
            throw new BankErrorInputData(CARD_NUMBER_16_DIGITS);
        }
    }

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

    public void validatedCVVCard(String cardCVV) {
        if (cardCVV == null || cardCVV.length() != 3 || !cardCVV.chars().allMatch(Character::isDigit)) {
            throw new BankErrorInputData(ENTER_CORRECT_CVV);
        }
    }

    public void validatedAmountCard(int amount) {
        if (amount <= 0) {
            throw new BankErrorInputData(AMOUNT_OPERATION_MORE_THEN_ZERO);
        }
    }
}
