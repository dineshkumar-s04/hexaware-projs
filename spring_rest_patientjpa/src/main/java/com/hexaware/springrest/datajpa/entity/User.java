package com.hexaware.springrest.datajpa.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "Users")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "patient")
@EqualsAndHashCode(exclude = "patient")
@Slf4j
public class User {

    @Id
    private int userId;

    private String username;

    private String password;

    private String role;

    @OneToOne
    @JoinColumn(name="patient_id")
    @JsonBackReference
    
    private Patient patient;

}