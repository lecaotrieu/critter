package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeEntity save(EmployeeEntity employee) {
        return employeeRepository.save(employee);
    }

    public EmployeeEntity findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public List<EmployeeEntity> findAll() {
        return employeeRepository.findAll();
    }

    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    public List<EmployeeEntity> findByService(DayOfWeek dayOfWeek, Set<EmployeeSkill> skills) {
        List<EmployeeEntity> employeesByAvailability = employeeRepository.findByDaysAvailableContaining(dayOfWeek);

        return employeesByAvailability.stream()
                .filter(employee -> employee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }

}
