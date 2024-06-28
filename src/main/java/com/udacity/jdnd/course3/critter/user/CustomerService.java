package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    @Transactional
    public CustomerEntity save(CustomerEntity customer) {
        CustomerEntity customerEntity = customerRepository.save(customer);
        for (PetEntity petEntity : customer.getPets()) {
            petEntity.setCustomer(customerEntity);
            petRepository.save(petEntity);
        }
        return customerEntity;
    }

    public CustomerEntity findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public List<CustomerEntity> findAll() {
        List<CustomerEntity> customerEntities = customerRepository.findAll();
        for(CustomerEntity customerEntity :customerEntities ) {
            customerEntity.setPets(petRepository.findPetEntitiesByCustomer_Id(customerEntity.getId()));
        }
        return customerEntities;
    }

    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    public CustomerEntity findByPetId(Long petId) {
        CustomerEntity customerEntity = customerRepository.findByPets_Id(petId);
        customerEntity.setPets(petRepository.findPetEntitiesByCustomer_Id(customerEntity.getId()));
        return customerEntity;
    }
}
