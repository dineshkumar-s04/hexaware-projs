package com.hexaware.springrest.datajpa;

import com.hexaware.springrest.datajpa.entity.Patient;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Slf4j

public class PatientDTO {
	
	
	private int patientId;
	private String name;
	private String gender;
	
	
	
	

}
