package com.bankconnect.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OnlinePaymentRequest {
    private String dotationType;
    private String bill;
    private String amount;
}
