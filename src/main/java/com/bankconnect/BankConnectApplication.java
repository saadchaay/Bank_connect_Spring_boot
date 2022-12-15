package com.bankconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BankConnectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankConnectApplication.class, args);
    }

    @GetMapping("/")
    public String hello(){
        System.out.println("Hello world");
        return "hello world";
    }

}
