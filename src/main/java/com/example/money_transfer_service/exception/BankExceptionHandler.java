package com.example.money_transfer_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BankExceptionHandler {
    @ExceptionHandler(BankErrorInputData.class)
    public ResponseEntity<String> bankErrorInputDataHandler(BankErrorInputData e) {
        String message = "Error input data";
        log.info(message + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);  //400
    }

    @ExceptionHandler(BankErrorConfirmation.class)
    public ResponseEntity<String> bankErrorConfirmationHandler(BankErrorConfirmation e) {
        String message = "Error confirmation";
        log.info(message + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); //500
    }

    @ExceptionHandler(BankErrorTransfer.class)
    public ResponseEntity<String> bankErrorTransferHandler(BankErrorTransfer e) {
        String message = "Error transfer";
        log.info(message + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); //500
    }
}
