package com.example.money_transfer_service.controller;

import com.example.money_transfer_service.model.RequestConfirmOperation;
import com.example.money_transfer_service.model.RequestTransfer;
import com.example.money_transfer_service.model.ResponsMoney;
import com.example.money_transfer_service.service.BankService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BankController {
    private final BankService bankService;


    @PostMapping("/transfer")
    public ResponsMoney transfer(@RequestBody RequestTransfer requestTransfer) {
        return bankService.transfer(requestTransfer);
    }

    @PostMapping("/confirmOperation")
    public ResponsMoney confirmOperation(@RequestBody RequestConfirmOperation requestConfirmOperation) {
        return bankService.confirmOperation(requestConfirmOperation);
    }

}
