package com.example.money_transfer_service;

import com.example.money_transfer_service.model.Amount;
import com.example.money_transfer_service.model.RequestConfirmOperation;
import com.example.money_transfer_service.model.RequestTransfer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferServiceApplicationTests {
    String host = "http://localhost:";
    String transferEndpoint = "/transfer";
    String confirmOperationEndpoint = "/confirmOperation";
    String expected = "{\"operationId\":\"1\"}";
    int port = 5500;
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Container
    private final GenericContainer<?> appContainer = new GenericContainer<>("money_transfer_service-app")
            .withExposedPorts(port);

    @Test
    void contextLoadsTransfer() {
        RequestTransfer requestTransfer = new RequestTransfer("1234567890123456",
                "02/24", "123", "1234567890123456",
                new Amount(200, "RUR"));
        ResponseEntity<String> forEntity = testRestTemplate.postForEntity(host + appContainer.getMappedPort(port)
                + transferEndpoint, requestTransfer, String.class);
      //  System.out.println(forEntity.getBody());
        Assertions.assertEquals(expected, forEntity.getBody());

        RequestConfirmOperation requestConfirmOperation = new RequestConfirmOperation("1", "0000");
        ResponseEntity<String> entity = testRestTemplate.postForEntity(host + appContainer.getMappedPort(port)
                + confirmOperationEndpoint, requestConfirmOperation, String.class);
        Assertions.assertEquals(expected, entity.getBody());
    }
}

