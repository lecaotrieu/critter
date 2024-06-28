package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.ScheduleEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerEntity customerEntity = convertToEntity(customerDTO);
        List<PetEntity> petEntities = new ArrayList<>();
        if (customerDTO.getPetIds() != null) {
            petEntities = customerDTO.getPetIds().stream().map(petId -> petService.findById(petId)).collect(Collectors.toList());
        }
        customerEntity.setPets(petEntities);
        customerEntity = customerService.save(customerEntity);
        customerDTO.setId(customerEntity.getId());
        return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerEntity> customerEntities = customerService.findAll();
        return customerEntities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        return convertToDTO(customerService.findByPetId(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = convertToEntity(employeeDTO);
        employeeEntity = employeeService.save(employeeEntity);
        return convertToDTO(employeeEntity);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertToDTO(employeeService.findById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        EmployeeEntity employeeEntity = employeeService.findById(employeeId);
        employeeEntity.setDaysAvailable(daysAvailable);
        employeeService.save(employeeEntity);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<EmployeeEntity> employeeEntities = employeeService.findByService(employeeDTO.getDate().getDayOfWeek(), employeeDTO.getSkills());
        return employeeEntities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private EmployeeEntity convertToEntity(EmployeeDTO employeeDTO) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        BeanUtils.copyProperties(employeeDTO, employeeEntity);
        return employeeEntity;
    }

    private EmployeeDTO convertToDTO(EmployeeEntity employeeEntity) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employeeEntity, employeeDTO);
        return employeeDTO;
    }

    private CustomerEntity convertToEntity(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = new CustomerEntity();
        BeanUtils.copyProperties(customerDTO, customerEntity);
        return customerEntity;
    }

    private CustomerDTO convertToDTO(CustomerEntity customerEntity) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customerEntity, customerDTO);
        if (customerEntity.getPets() != null) {
            List<Long> petIds =  customerEntity.getPets().stream().map(PetEntity::getId).collect(Collectors.toList());
            customerDTO.setPetIds(petIds);
        }
        return customerDTO;
    }
}
