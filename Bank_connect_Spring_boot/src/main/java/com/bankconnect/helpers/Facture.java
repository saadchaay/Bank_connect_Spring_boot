package com.bankconnect.helpers;

import com.bankconnect.entities.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@RequiredArgsConstructor
public class Facture {

    private Account account;
    private int number;
    private String refBill;
    private String type;
    private LocalDate date;
    private Double amount;

    public Facture(Account account, int number, String refBill, String billType, Double amount) {
        this.account = account;
        this.number = number;
        this.refBill = refBill;
        this.type = billType;
        this.date = LocalDate.now();
        this.amount = amount;
    }
}
