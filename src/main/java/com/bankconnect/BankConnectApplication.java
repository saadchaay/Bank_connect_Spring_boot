package com.bankconnect;

import com.bankconnect.helpers.SendSMS;
import lombok.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class BankConnectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankConnectApplication.class, args);
    }

}
