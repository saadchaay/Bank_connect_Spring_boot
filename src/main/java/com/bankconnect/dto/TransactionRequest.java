package com.bankconnect.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TransactionRequest {
    private String accountId;
    private String amount;
    private String transactionType;
}
