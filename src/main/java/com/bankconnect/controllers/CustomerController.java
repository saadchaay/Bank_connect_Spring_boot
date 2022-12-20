package com.bankconnect.controllers;

import com.bankconnect.dto.RegisterRequest;
import com.bankconnect.entities.Customer;
import com.bankconnect.entities.Request;
import com.bankconnect.helpers.AuthenticatedUserInfo;
import com.bankconnect.helpers.TwilioSMS;
import com.bankconnect.repositories.RequestRepository;
import com.bankconnect.services.CustomerService;
import com.twilio.rest.api.v2010.account.Message;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService cstService;
    private final RequestRepository reqRepository;
    private final AuthenticatedUserInfo authUserInfo;

    @Value("${TWILIO_ACCOUNT_SID}")
    private String TWILIO_SID;

    @Value("${TWILIO_AUTH_TOKEN}")
    private String TWILIO_AUTH_TOKEN;

    @Value("${TWILIO_PHONE_NUMBER}")
    private String TWILIO_PHONE_NUMBER;

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
                false
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

    @GetMapping("test")
    public ResponseEntity<String> testSMS(){
        Message msg = TwilioSMS.sendSMS(TWILIO_SID, TWILIO_AUTH_TOKEN, TWILIO_PHONE_NUMBER);
        return ResponseEntity.ok(msg.getSid());
    }

    @GetMapping("info")
    public ResponseEntity<Customer> getCustomerInfo(HttpServletRequest req){
        String email = authUserInfo.getEmail(req);
        System.out.println(email);
        return ResponseEntity.ok(cstService.getCustomerByEmail(email));
    }

}
