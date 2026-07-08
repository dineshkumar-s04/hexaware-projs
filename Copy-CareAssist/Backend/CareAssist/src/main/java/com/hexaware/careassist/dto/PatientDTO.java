package com.hexaware.careassist.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {

	
	private int patientId;
	private int userId;
	private LocalDate dob;
	private String gender;
	private String symptoms;
	private String treatment;
	private String address;
}
