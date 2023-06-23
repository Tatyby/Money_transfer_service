package com.example.money_transfer_service.controller;

import com.example.money_transfer_service.model.RequestConfirmOperation;
import com.example.money_transfer_service.model.RequestTransfer;
import com.example.money_transfer_service.model.ResponsMoney;
import com.example.money_transfer_service.service.BankService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin("https://serp-ya.github.io/card-transfer/")
public class BankController {
    private final BankService bankService;

    @PostMapping("/transfer")
    public ResponseEntity<ResponsMoney> transfer(@RequestBody RequestTransfer requestTransfer) {
        return new ResponseEntity<>(bankService.transfer(requestTransfer), HttpStatus.OK);
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<ResponsMoney> confirmOperation(@RequestBody RequestConfirmOperation requestConfirmOperation) {
        return new ResponseEntity<>(bankService.confirmOperation(requestConfirmOperation), HttpStatus.OK);
    }
}
