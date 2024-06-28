package com.udacity.jdnd.course3.critter.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    @Transactional
    public PetEntity save(PetEntity pet) {
        return petRepository.save(pet);
    }

    public PetEntity findById(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    public List<PetEntity> findAll() {
        return petRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        petRepository.deleteById(id);
    }

    public List<PetEntity> findAllByCustomer(Long customerId) {
        return petRepository.findPetEntitiesByCustomer_Id(customerId);
    }
}
