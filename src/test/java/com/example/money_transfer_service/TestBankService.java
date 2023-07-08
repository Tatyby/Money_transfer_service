package com.example.money_transfer_service;

import com.example.money_transfer_service.model.Amount;
import com.example.money_transfer_service.model.RequestConfirmOperation;
import com.example.money_transfer_service.model.RequestTransfer;
import com.example.money_transfer_service.model.ResponsMoney;
import com.example.money_transfer_service.repository.BankRepository;
import com.example.money_transfer_service.service.BankService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestBankService {
    BankRepository bankRepository = new BankRepository();
    @Mock
    BankService bankServiceMock;
    ResponsMoney responseMoneyMock = new ResponsMoney("1");
    @Test
    public void checkTest() {
        int amount = bankRepository.getAmountFromValue();
        int commission = amount / 100;
        boolean expected = bankServiceMock.check(commission, amount);
        Assert.assertFalse(expected);
    }

    @Test
    public void transferTest() {

        RequestTransfer requestTransfer = new RequestTransfer("1234567890123456",
                "02/24", "123", "1234567890123456",
                new Amount(200, "RUR"));
        Mockito.when(bankServiceMock.transfer(requestTransfer)).thenReturn(responseMoneyMock);
        Assert.assertEquals(bankServiceMock.transfer(requestTransfer).operationId(), "1");
    }

    @Test
    public void confirmOperationTest() {
        RequestConfirmOperation requestConfirmOperation = new RequestConfirmOperation("1",
                "0000");
        Mockito.when(bankServiceMock.confirmOperation(requestConfirmOperation)).thenReturn(responseMoneyMock);
        Assert.assertEquals(bankServiceMock.confirmOperation(requestConfirmOperation).operationId(), "1");

    }
}
