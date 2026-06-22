package com.hexaware.patient.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

@Entity
public class Patient {

	@Id
	private int pid;
	private String pname;
	private String gender;
	
}
