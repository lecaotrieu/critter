package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.PetEntity;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.EmployeeEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;
    private EmployeeRepository employeeRepository;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        ScheduleEntity scheduleEntity = scheduleService.save(convertToEntity(scheduleDTO));
        return convertToDTO(scheduleEntity);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleEntity> scheduleEntities = scheduleService.findAll();
        return scheduleEntities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleEntity> scheduleEntities = scheduleService.findByPetId(petId);
        return scheduleEntities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleEntity> scheduleEntities = scheduleService.findByEmployeeId(employeeId);
        return scheduleEntities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleEntity> scheduleEntities = scheduleService.findByCustomerId(customerId);
        return scheduleEntities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PetService petService;

    private ScheduleEntity convertToEntity(ScheduleDTO scheduleDTO) {
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        BeanUtils.copyProperties(scheduleDTO, scheduleEntity);
        if (scheduleDTO.getEmployeeIds() != null) {
            List<EmployeeEntity> employees = scheduleDTO.getEmployeeIds().stream().map(employeeId -> employeeService.findById(employeeId)).collect(Collectors.toList());
            scheduleEntity.setEmployees(employees);
        }
        if (scheduleDTO.getPetIds() != null) {
            List<PetEntity> pets = scheduleDTO.getPetIds().stream().map(petId -> petService.findById(petId)).collect(Collectors.toList());
            scheduleEntity.setPets(pets);
        }
        return scheduleEntity;
    }

    private ScheduleDTO convertToDTO(ScheduleEntity scheduleEntity) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(scheduleEntity, scheduleDTO);
        if (scheduleEntity.getEmployees() != null) {
            List<Long> employeeIds =  scheduleEntity.getEmployees().stream().map(EmployeeEntity::getId).collect(Collectors.toList());
            scheduleDTO.setEmployeeIds(employeeIds);
        }
        if (scheduleEntity.getPets() != null) {
            List<Long> petIds =  scheduleEntity.getPets().stream().map(PetEntity::getId).collect(Collectors.toList());
            scheduleDTO.setPetIds(petIds);
        }
        return scheduleDTO;
    }
}
