package com.hexaware.careassist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProviderDTO {

	
	private int providerId;
	private int userId;
	private String hospitalName;
	private String licenseNumber;
	private String specialization;
	private String address;
}
