package com.hexaware.careassist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimDocumentDTO {

	private int documentId;
	private int claimId;
	private String fileName;
	private String filePath;
}
