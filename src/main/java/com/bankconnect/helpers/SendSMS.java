package com.bankconnect.helpers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RestController;

@Getter
@Setter
@RestController
public class SendSMS {

    @Value("${twilio.sID}")
    private String twilio_sId;

}
