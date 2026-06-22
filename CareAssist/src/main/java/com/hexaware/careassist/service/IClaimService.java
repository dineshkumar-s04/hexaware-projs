package com.hexaware.careassist.service;

import java.util.List;
import com.hexaware.careassist.dto.ClaimDTO;

public interface IClaimService {

	ClaimDTO submitClaim(ClaimDTO claimDTO);

	ClaimDTO getClaimById(Integer claimId);

	List<ClaimDTO> getAllClaims();

	ClaimDTO approveClaim(Integer claimId);

	ClaimDTO rejectClaim(Integer claimId, String reason);
}