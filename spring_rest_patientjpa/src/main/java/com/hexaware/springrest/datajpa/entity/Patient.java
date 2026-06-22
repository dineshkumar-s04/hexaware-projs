package com.hexaware.springrest.datajpa.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "Patients")
@NamedQueries(@NamedQuery(
        name = "Patient.getGender",
        query = "select p from Patient p where p.gender = ?1"))

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"user", "insuranceList"})
@EqualsAndHashCode(exclude = {"user", "insuranceList"})
@Slf4j
public class Patient {

    @Id
    private int patientId;

    private String name;

    private String gender;

    // One Patient -> One User
    @OneToOne
    @JoinColumn(name="patient_id")
    @JsonIgnore
    private Patient patient;

    // One Patient -> Many Insurance Policies
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<PatientInsurance> insuranceList;
}