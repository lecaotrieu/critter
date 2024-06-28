package com.udacity.jdnd.course3.critter.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<PetEntity, Long> {
    List<PetEntity> findPetEntitiesByCustomer_Id(Long customerId);
    List<PetEntity> findPetEntitiesBySchedules_Id(Long scheduleId);
}
