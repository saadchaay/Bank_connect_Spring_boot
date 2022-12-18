package com.bankconnect.controllers;

import com.bankconnect.dto.RegisterRequest;
import com.bankconnect.entities.Customer;
import com.bankconnect.entities.Request;
import com.bankconnect.helpers.Enum;
import com.bankconnect.repositories.RequestRepository;
import com.bankconnect.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService cstService;
    private final RequestRepository reqRepository;

    @PostMapping("register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest req
            ){
        Customer customer = new Customer(
                req.getName(),
                req.getEmail(),
                req.getPassword(),
                req.getPhone(),
                req.getCin(),
                req.getAddress(),
                req.getCinImage(),
                Enum.statusVal.Pending.toString()
        );
        Request reqAccount = new Request();
        customer.setPassword(BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt(10)));
        if(cstService.save(customer) != null){
            reqAccount.setTypeAccount(req.getAccType());
            reqAccount.setCustomerId(customer.getId());
            reqRepository.save(reqAccount);
            return ResponseEntity.ok("Success registration");
        }else{
            return ResponseEntity.status(400).body("Failed creation customer");
        }
    }

}
