package com.bankconnect.helpers;

public interface Enum {
    enum role {CUSTOMER, AGENT}
    enum statusVal {Pending, Confirmed, Rejected}
    enum transactionType {Transfer, Withdrawal, Deposit, OnlinePayment, BillPayment}
    enum accType {Standard, Professional}
    enum bills {Phone, Water, Electricity}
    enum dotationType {National, International}
}
