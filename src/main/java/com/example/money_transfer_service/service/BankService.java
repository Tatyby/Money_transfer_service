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

    public ResponsMoney transfer(RequestTransfer requestTransfer) { //возвращает id операции


        final String cardFromNumber = requestTransfer.cardFromNumber();
        final String cardFromValidTill = requestTransfer.cardFromValidTill();
        final String cardFromCVV = requestTransfer.cardFromCVV();
        final String cardToNumber = requestTransfer.cardToNumber();
        final int amount = requestTransfer.amount().value();
        final int commission = amount / 100;
        ValidatedImpl validated = new ValidatedImpl();

        validated.validatedNumberCard(cardFromNumber);
        validated.validatedNumberCard(cardToNumber);
        validated.validatedCVVCard(cardFromCVV);
        validated.validatedDataCard(cardFromValidTill);
        validated.validatedAmountCard(amount);

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
            throw new BankErrorConfirmation(INVALID_CODE);//500 ошибка
        }
    }
}

