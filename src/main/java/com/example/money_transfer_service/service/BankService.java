package com.example.money_transfer_service.service;

import com.example.money_transfer_service.model.RequestConfirmOperation;
import com.example.money_transfer_service.model.RequestTransfer;
import com.example.money_transfer_service.model.ResponsMoney;
import com.example.money_transfer_service.repository.BankRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class BankService {
    private BankRepository bankRepository;

    public ResponsMoney transfer(RequestTransfer requestTransfer) { //возвращает id операции

        final String cardFromNumber = requestTransfer.cardFromNumber();
        final String cardFromValidTill = requestTransfer.cardFromValidTill();
        final String cardFromCVV = requestTransfer.cardFromCVV();
        final String cardToNumber = requestTransfer.cardToNumber();
        final int amount = requestTransfer.amount().value();
        final int commission = amount / 100;

        if (check()) {
            final String idOperation = String.valueOf(bankRepository.getId());
            log.info("Перевод c карты {} на карту {} в размере {}, с комиссией {} - выполнен успешно", cardFromNumber, cardToNumber, amount, commission);
            return new ResponsMoney(idOperation);
        } else {
            throw new RuntimeException("Недостаточно средст на карте");
        }
    }

    private boolean check() { //метод проверяющий номер карт, свс код, есть ли средства на карте
        return true;
    }

    public ResponsMoney confirmOperation(RequestConfirmOperation requestConfirmOperation) {
        final String idOperation = requestConfirmOperation.operationId();
        return new ResponsMoney(idOperation);

    }
}
