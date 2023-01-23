package com.bankconnect.helpers;

import com.bankconnect.entities.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@RequiredArgsConstructor
public class Facture {

    private Account account;
    private Long number;
    private String type;
    private Date date;
    private Double amount;

}
