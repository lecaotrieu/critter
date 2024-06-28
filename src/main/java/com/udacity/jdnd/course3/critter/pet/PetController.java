package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.CustomerEntity;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        PetEntity petEntity = convertToEntity(petDTO);
        CustomerEntity customerEntity = customerService.findById(petDTO.getOwnerId());
        if (customerEntity != null) {
            petEntity.setCustomer(customerEntity);
        }
        petEntity = petService.save(petEntity);
        return convertToDTO(petEntity);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertToDTO(petService.findById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetEntity> petEntities = petService.findAll();
        return petEntities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetEntity> petEntities = petService.findAllByCustomer(ownerId);
        return petEntities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private PetEntity convertToEntity(PetDTO petDTO) {
        PetEntity petEntity = new PetEntity();
        BeanUtils.copyProperties(petDTO, petEntity);
        return petEntity;
    }

    private PetDTO convertToDTO(PetEntity petEntity) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(petEntity, petDTO);
        if (petEntity.getCustomer() != null) {
            petDTO.setOwnerId(petEntity.getCustomer().getId());
        }
        return petDTO;
    }
}
