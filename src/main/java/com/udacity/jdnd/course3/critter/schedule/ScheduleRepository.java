package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    List<ScheduleEntity> findByPets_Id(Long petId);
    List<ScheduleEntity> findByEmployees_Id(Long employeeId);
    List<ScheduleEntity> findByPets_Customer_Id(Long customerId);
}
