package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;

    @Transactional
    public ScheduleEntity save(ScheduleEntity schedule) {
        return scheduleRepository.save(schedule);
    }

    public ScheduleEntity findById(Long id) {
        return scheduleRepository.findById(id).orElse(null);
    }

    public List<ScheduleEntity> findAll() {
        List<ScheduleEntity> scheduleEntities = scheduleRepository.findAll();
        for(ScheduleEntity scheduleEntity : scheduleEntities) {
            scheduleEntity.setEmployees(employeeRepository.findEmployeeEntitiesBySchedules_Id(scheduleEntity.getId()));
            scheduleEntity.setPets(petRepository.findPetEntitiesBySchedules_Id(scheduleEntity.getId()));
        }
        return scheduleEntities;
    }

    @Transactional
    public void deleteById(Long id) {
        scheduleRepository.deleteById(id);
    }

    public List<ScheduleEntity> findByPetId(Long PetId) {
        return scheduleRepository.findByPets_Id(PetId);
    }

    public List<ScheduleEntity> findByEmployeeId(Long employeeId) {
        return scheduleRepository.findByEmployees_Id(employeeId);
    }

    public List<ScheduleEntity> findByCustomerId(Long customerId) {
        return scheduleRepository.findByPets_Customer_Id(customerId);
    }
}
