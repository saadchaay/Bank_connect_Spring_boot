package com.bankconnect.services;

import com.bankconnect.entities.Request;
import com.bankconnect.repositories.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;

    public List<Request> listAll(){
        return requestRepository.findAll();
    }

    public Request getRequestByCustomerId(Long id){
        return requestRepository.findByCustomerId(id);
    }
}
