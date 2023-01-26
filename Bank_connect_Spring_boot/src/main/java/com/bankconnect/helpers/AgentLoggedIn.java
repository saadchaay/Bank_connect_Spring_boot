package com.bankconnect.helpers;

import com.bankconnect.entities.Agent;
import com.bankconnect.entities.Customer;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class AgentLoggedIn {
    private Agent agent;
    private String Token;
}
