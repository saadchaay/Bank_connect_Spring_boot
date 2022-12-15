package com.bankconnect.entities;

import jakarta.persistence.*;

@Entity
public class Virement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "rec_account", nullable = false)
    private Long recAccount;

    @Basic
    @Column(name = "transaction_id", nullable = false)
    private Long transactionId;

    @Basic
    @Column(name = "status", nullable = false)
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "rec_account", insertable = false, updatable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "transaction_id", insertable = false, updatable = false)
    private Transaction transaction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecAccount() {
        return recAccount;
    }

    public void setRecAccount(Long recAccount) {
        this.recAccount = recAccount;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
