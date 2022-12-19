package com.bankconnect.helpers;

public interface Enum {
    enum role {CUSTOMER, AGENT}
    enum statusVal {Pending, Confirmed, Rejected}
    enum transactionType {Transfer, Withdrawal, Deposit, OnlinePayment, FacturePayment}
    enum accType {Standard, Professional}
}
