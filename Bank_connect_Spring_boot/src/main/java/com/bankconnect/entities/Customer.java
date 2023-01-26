package com.bankconnect.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    @Basic
    @Column(name = "phone", nullable = false)
    private String phone;

    @Basic
    @Column(name = "cin", nullable = false)
    private String cin;

    @Basic
    @Column(name = "address", nullable = false)
    private String address;

    @Basic
    @Column(name = "cin_image", nullable = false)
    private String cinImage;

    @Basic
    @Column(name = "status", nullable = false)
    private boolean status;

    @JsonIgnore
    @OneToMany(mappedBy="customer", cascade = CascadeType.REMOVE)
    private List<Account> accounts;

    @JsonIgnore
    @OneToMany(mappedBy="customer", cascade = CascadeType.REMOVE)
    private List<Request> requests;

    public Customer() {}

    public Customer(String name, String email, String password, String phone, String cin, String address, String cinImage, boolean status) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.cin = cin;
        this.address = address;
        this.cinImage = cinImage;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCinImage() {
        return cinImage;
    }

    public void setCinImage(String cinImage) {
        this.cinImage = cinImage;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
}
