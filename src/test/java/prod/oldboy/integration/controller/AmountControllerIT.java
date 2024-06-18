package prod.oldboy.integration.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import prod.oldboy.database.entity.Amount;
import prod.oldboy.dto.amount_dto.AmountTransferDto;
import prod.oldboy.integration.IntegrationTestBase;
import prod.oldboy.repository.amount_repo.AmountRepository;
import prod.oldboy.security.JwtTokenGenerator;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class AmountControllerIT extends IntegrationTestBase {

    private final MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private final AmountRepository amountRepository;
    private String jwtToken;
    private final JwtTokenGenerator jwtTokenGenerator;
    private final BigDecimal enoughMoneyToTransfer = BigDecimal.valueOf(50);
    private final BigDecimal toBigMoneyToTransfer = BigDecimal.valueOf(10000);
    private BigDecimal amountTransferFrom;
    private final Long accountIdTransferFrom = 1L;
    private final Long accountIdTransferTo = 2L;

    @BeforeEach
    public void setup() {
        Amount amount = amountRepository.findAmountByAccountId(accountIdTransferFrom).get();
        String loginTransferFrom = amount.getAccount().getLogin();
        amountTransferFrom = amount.getAmount();
        jwtToken = "Bearer " + jwtTokenGenerator.getToken(accountIdTransferFrom,loginTransferFrom);
        objectMapper = new ObjectMapper();
    }

    @Test
    void goodTransferMoneyControllerTest() throws Exception {
        mockMvc.perform(put("/api/v1/users/amounts/transfer")
                        .header("Authorization", jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new AmountTransferDto(accountIdTransferTo, enoughMoneyToTransfer)))
                ).andExpectAll(
                        status().is2xxSuccessful(),
                        jsonPath("amount").value(amountTransferFrom.subtract(enoughMoneyToTransfer))
                );
    }

    @Test
    void badTransferMoneyControllerTest() throws Exception {
        mockMvc.perform(put("/api/v1/users/amounts/transfer")
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        new AmountTransferDto(accountIdTransferTo, toBigMoneyToTransfer)))
        ).andExpectAll(
                status().is2xxSuccessful(),
                jsonPath("amount").value(amountTransferFrom)
        );
    }
}