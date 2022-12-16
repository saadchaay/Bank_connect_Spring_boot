package com.bankconnect.configs;

import com.bankconnect.entities.Agent;
import com.bankconnect.services.AgentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;


public class AgentConfig {

//    @Bean
    CommandLineRunner commandLineRunner(AgentService agService){
        return args -> {
            Agent agent = new Agent("agent@bank-connect.com", "$2y$10$RVIo5RnOOfuYLoMkMK2js.MouZv.vywNuviWFofY4LMuLpQNfB/Ei");
            agService.save(agent);
        };
    }
}
