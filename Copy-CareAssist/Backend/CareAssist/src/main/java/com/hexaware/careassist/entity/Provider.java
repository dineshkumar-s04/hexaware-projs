package com.hexaware.careassist.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "providers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_id")
    private int providerId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "hospital_name")
    private String hospitalName;

    @Column(name = "license_number")
    private String licenseNumber;

    private String specialization;

    private String address;
}