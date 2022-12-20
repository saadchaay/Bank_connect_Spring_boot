package com.bankconnect.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DepotWithdrawRequest {
    private Long accountNumber;
    private double amount;

}
