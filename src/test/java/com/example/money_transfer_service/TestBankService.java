package com.example.money_transfer_service;

import com.example.money_transfer_service.exception.BankErrorInputData;
import com.example.money_transfer_service.model.Amount;
import com.example.money_transfer_service.model.RequestTransfer;
import com.example.money_transfer_service.model.ResponsMoney;
import com.example.money_transfer_service.repository.BankRepository;
import com.example.money_transfer_service.service.BankService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;


import java.util.stream.Stream;

public class TestBankService {
    BankRepository bankRepository = new BankRepository();
    BankService bankService = new BankService(bankRepository);

    @Test
    public void checkTest() {

        int amount = bankRepository.getAmountFromValue();
        int commission = amount / 100;
        boolean expected = bankService.check(commission, amount);
        Assert.assertFalse(expected);
    }

    @Test
    public void validatedNumberCardTest() {
        String cardNumber = "12345678901234567"; //17  цифр
        BankErrorInputData thrown = Assertions.assertThrows(
                BankErrorInputData.class,
                () -> bankService.validatedNumberCard(cardNumber)
        );
        Assert.assertTrue(thrown.getMessage().contains("Номер карты должен состоять из 16 цифр"));
    }

    @ParameterizedTest
    @MethodSource("addTestParameterizedValidatedDataCardTest")
    public void validatedDataCardTest(String cardValidTill, String expected) {
        BankErrorInputData thrown = Assertions.assertThrows(
                BankErrorInputData.class,
                () -> bankService.validatedDataCard(cardValidTill)
        );
        Assert.assertEquals(expected, thrown.getMessage());
    }

    public static Stream<Arguments> addTestParameterizedValidatedDataCardTest() {
        return Stream.of(
                Arguments.of("13/23", "Введите корректный месяц"),
                Arguments.of(null, "Введите срок действия карты"),
                Arguments.of("12/22", "Истек срок действия карты, неверный год"),
                Arguments.of("03/23", "Истек срок действия карты, неверный месяц")
        );
    }

    @ParameterizedTest
    @MethodSource("addTestParameterizedValidatedCVVCardTest")
    public void validatedCVVCardTest(String cvv, String expected) {
        BankErrorInputData thrown = Assertions.assertThrows(
                BankErrorInputData.class,
                () -> bankService.validatedCVVCard(cvv)
        );
        Assert.assertEquals(expected, thrown.getMessage());
    }

    public static Stream<Arguments> addTestParameterizedValidatedCVVCardTest() {
        String message = "Введите корректный CVV код";
        return Stream.of(
                Arguments.of(null, message),
                Arguments.of("1234", message),
                Arguments.of("12", message),
                Arguments.of("ASD", message)
        );

    }

    @ParameterizedTest
    @MethodSource("addTestParameterizedValidatedAmountCardTest")
    public void validatedAmountCardTest(int amount, String expected) {
        BankErrorInputData thrown = Assertions.assertThrows(
                BankErrorInputData.class,
                () -> bankService.validatedAmountCard(amount)
        );
        Assert.assertEquals(expected, thrown.getMessage());
    }

    public static Stream<Arguments> addTestParameterizedValidatedAmountCardTest() {
        String message = "Сумма операции должна быть больше нуля";
        return Stream.of(
                Arguments.of(0, message),
                Arguments.of(-10, message)
        );
    }

    @Test
    public void transferTest() {
        String id = "1";
        RequestTransfer requestTransfer = new RequestTransfer("1234567890123456",
                "02/24", "123", "1234567890123456",
                new Amount(200, "RUR"));
        BankService bankServiceMock = Mockito.mock(BankService.class);
        ResponsMoney responsMoneyMock =Mockito.mock(ResponsMoney.class);
        Mockito.when(bankServiceMock.transfer(requestTransfer)).thenReturn(responsMoneyMock);


    }
}
