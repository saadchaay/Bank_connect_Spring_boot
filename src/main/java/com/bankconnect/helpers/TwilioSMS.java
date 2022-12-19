package com.bankconnect.helpers;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class TwilioSMS {

    public static Message sendSMS(String sId, String authToken, String phone){
        Twilio.init(sId, authToken);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+212708953404"),
                        new com.twilio.type.PhoneNumber(phone),
                        "Welcome Bank connect: \n\nThis code for 536787.")
                .create();
        System.out.println(message.getTo());
        return message;
    }
}
