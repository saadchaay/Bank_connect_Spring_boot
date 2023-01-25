package com.bankconnect.helpers;

import com.bankconnect.entities.Customer;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class LoggedIn {
    private Customer customer;
    private String Token;

}
