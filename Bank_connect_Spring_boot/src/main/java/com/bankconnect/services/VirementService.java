package com.bankconnect.services;

import com.bankconnect.entities.Virement;
import com.bankconnect.repositories.VirementRepository;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VirementService {
    private final VirementRepository virementRepository;

    @Autowired
    public VirementService(VirementRepository virementRepository) {
        this.virementRepository = virementRepository;
    }

    public Virement save(Virement virement){
        return virementRepository.save(virement);
    }

    public List<Virement> getAllVirments(){
        return virementRepository.findAll();
    }

    public Virement getVirmentById(Long id){
        Optional virment = virementRepository.findById(id);
        return virment.isPresent()? (Virement) virment.get() : null;
    }
}
