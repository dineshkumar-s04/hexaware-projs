package com.hexaware.careassist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsuranceCompanyDTO {
	
	
	private int companyId;
	private int userId;
	private String companyName;
	private String licenseNumber;
	private String address;
}
