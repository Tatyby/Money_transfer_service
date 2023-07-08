package com.example.money_transfer_service;

import com.example.money_transfer_service.exception.BankErrorInputData;
import com.example.money_transfer_service.service.ValidatedImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TestValidatedImpl {
    ValidatedImpl validated = new ValidatedImpl();
    @Test
    public void validatedNumberCardTest() {
        String cardNumber = "12345678901234567"; //17  цифр
        BankErrorInputData thrown = Assertions.assertThrows(
                BankErrorInputData.class,
                () -> validated.validatedNumberCard(cardNumber)
        );
        Assert.assertTrue(thrown.getMessage().contains("Номер карты должен состоять из 16 цифр"));
    }

    @ParameterizedTest
    @MethodSource("addTestParameterizedValidatedDataCardTest")
    public void validatedDataCardTest(String cardValidTill, String expected) {
        BankErrorInputData thrown = Assertions.assertThrows(
                BankErrorInputData.class,
                () -> validated.validatedDataCard(cardValidTill)
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
                () -> validated.validatedCVVCard(cvv)
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
                () -> validated.validatedAmountCard(amount)
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
}
