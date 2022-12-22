package com.bankconnect.controllers;

import com.bankconnect.dto.RegisterRequest;
import com.bankconnect.entities.Customer;
import com.bankconnect.entities.Request;
import com.bankconnect.helpers.AuthenticatedUserInfo;
import com.bankconnect.helpers.SendMail;
import com.bankconnect.helpers.TwilioSMS;
import com.bankconnect.repositories.RequestRepository;
import com.bankconnect.services.CustomerService;
import com.twilio.rest.api.v2010.account.Message;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService cstService;
    private final RequestRepository reqRepository;
    private final AuthenticatedUserInfo authUserInfo;
    private final SendMail sendMail;
    private Customer cst;
    private int codeVer;

    @Value("${EMAIL_ADDRESS}")
    private String email;

    @Value("${EMAIL_PASSWORD}")
    private String password;

    @PostMapping("register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest req
            ){
        cst = new Customer(
                req.getName(),
                req.getEmail(),
                req.getPassword(),
                req.getPhone(),
                req.getCin(),
                req.getAddress(),
                req.getCinImage(),
                false
        );
//        Request reqAccount = new Request();
        cst.setPassword(BCrypt.hashpw(cst.getPassword(), BCrypt.gensalt(10)));
        codeVer = generateVerifiedCode();
        System.out.println(codeVer);
//        sendMail.sendSimpleMessage(customer.getEmail(), "Code verification", "Code: "+codeVer+" .");
        return ResponseEntity.ok("Code verification has been sent to you.");
//        if(cstService.save(customer) != null){
//            reqAccount.setTypeAccount(req.getAccType());
//            reqAccount.setCustomerId(customer.getId());
//            reqRepository.save(reqAccount);
//            return ResponseEntity.ok("Success registration");
//        }else{
//            return ResponseEntity.status(400).body("Failed creation customer");
//        }
    }

    @PostMapping("/code-verification")
    public ResponseEntity<String> verRegistration(
            @RequestBody RegisterRequest req){
        Request reqAccount = new Request();
        if((req.getVerCode() == codeVer) && (cst != null)){
            if(cstService.save(cst) != null) {
                reqAccount.setTypeAccount(req.getAccType());
                reqAccount.setCustomerId(cst.getId());
                reqRepository.save(reqAccount);
                return ResponseEntity.ok("Success registration");
            }
        }
        return ResponseEntity.status(400).body("Failed creation customer");
    }

    @GetMapping("info")
    public ResponseEntity<Customer> getCustomerInfo(HttpServletRequest req){
        String email = authUserInfo.getEmail(req);
        System.out.println(email);
        return ResponseEntity.ok(cstService.getCustomerByEmail(email));
    }

    public int generateVerifiedCode(){
        return (int)Math.floor(Math.random()*(99999-9999+1)+9999);
    }
}
