package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.schedule.ScheduleEntity;
import com.udacity.jdnd.course3.critter.user.CustomerEntity;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "pet")
public class PetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private PetType type;
    private String name;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

//    @ElementCollection(targetClass = DayOfWeek.class)
//    @Enumerated(EnumType.STRING)
//    @CollectionTable(name = "pet_daysavailable", joinColumns = @JoinColumn(name = "pet_id"))
//    private Set<DayOfWeek> daysAvailable;

    @ManyToMany(mappedBy = "pets")
    private List<ScheduleEntity> schedules;

    private LocalDate birthDate;
    private String notes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public List<ScheduleEntity> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleEntity> schedules) {
        this.schedules = schedules;
    }


//    public Set<DayOfWeek> getDaysAvailable() {
//        return daysAvailable;
//    }
//
//    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
//        this.daysAvailable = daysAvailable;
//    }
}
