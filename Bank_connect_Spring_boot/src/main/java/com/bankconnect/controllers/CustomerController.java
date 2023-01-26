package com.bankconnect.controllers;

import com.bankconnect.dto.DepotWithdrawRequest;
import com.bankconnect.dto.RegisterRequest;
import com.bankconnect.entities.Account;
import com.bankconnect.entities.Customer;
import com.bankconnect.entities.Request;
import com.bankconnect.helpers.AuthenticatedUserInfo;
import com.bankconnect.helpers.RandomCode;
import com.bankconnect.helpers.SendMail;
import com.bankconnect.repositories.AccountRepository;
import com.bankconnect.repositories.RequestRepository;
import com.bankconnect.services.AccountService;
import com.bankconnect.services.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService cstService;
    private final RequestRepository reqRepository;
    private final AuthenticatedUserInfo authUserInfo;
    private final AccountService accService;
    private final SendMail sendMail;
    private Customer cst;
    private int codeVer;
    private LocalTime setTime;

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

        cst.setPassword(BCrypt.hashpw(cst.getPassword(), BCrypt.gensalt(10)));
        codeVer = RandomCode.generate();
        System.out.println(codeVer);
        sendMail.sendVerificationCode(cst.getEmail(), "Code verification", "Code: "+codeVer+" .");
        setTime = LocalTime.now();
        return ResponseEntity.ok("Code verification has been sent to you.");
    }

    @PostMapping("/code-verification")
    public ResponseEntity<String> verRegistration(
            @RequestBody RegisterRequest req){
        Request reqAccount = new Request();
        if((req.getVerCode() == codeVer) && (cst != null)){
            if(isCodeValid()){
                System.out.println("Valid code");
                if(cstService.save(cst) != null) {
                    reqAccount.setTypeAccount(req.getAccType());
                    reqAccount.setCustomerId(cst.getId());
                    reqRepository.save(reqAccount);
                    cst = null;
                    return ResponseEntity.ok("Success registration");
                }
            }else{
                ResponseEntity.status(400).body("The code has expired");
            }
//
        }
        return ResponseEntity.status(400).body("Failed creation customer");
    }

    @GetMapping("/resend-code")
    public ResponseEntity<String> resendVerCode(){
        codeVer = RandomCode.generate();
        sendMail.sendVerificationCode(cst.getEmail(), "Code verification", "Code: "+codeVer+" .");
        setTime = LocalTime.now();
        return ResponseEntity.ok("The code has been sent to you, check your email.");
    }

    @GetMapping("info")
    public ResponseEntity<Customer> getCustomerInfo(HttpServletRequest req){
        String email = authUserInfo.getEmail(req);
        System.out.println(email);
        return ResponseEntity.ok(cstService.getCustomerByEmail(email));
    }

    @GetMapping("customer/account")
    public ResponseEntity<Account> getAccountByEmail(HttpServletRequest httpSerReq){
        String email = authUserInfo.getEmail(httpSerReq);
        return ResponseEntity.ok(accService.getAccByEmail(email));
    }


    public boolean isCodeValid(){
        LocalTime now = LocalTime.now();
        return now.isBefore(setTime.plusMinutes(3));
    }
}
