package com.bankconnect.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class TransferRequest {
    private Long accountId;
    private Long recipientAccountNumber;
    private double amount;
}
