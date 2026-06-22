package com.hexaware.careassist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careassist.dto.ClaimDTO;
import com.hexaware.careassist.entity.Claim;
import com.hexaware.careassist.entity.InsuranceCompany;
import com.hexaware.careassist.entity.Invoice;
import com.hexaware.careassist.entity.Patient;
import com.hexaware.careassist.exception.ClaimNotFoundException;
import com.hexaware.careassist.exception.InsuranceCompanyNotFoundException;
import com.hexaware.careassist.exception.InvoiceNotFoundException;
import com.hexaware.careassist.exception.PatientNotFoundException;
import com.hexaware.careassist.repository.ClaimRepository;
import com.hexaware.careassist.repository.InsuranceCompanyRepository;
import com.hexaware.careassist.repository.InvoiceRepository;
import com.hexaware.careassist.repository.PatientRepository;
import com.hexaware.careassist.service.IClaimService;

@Service
public class ClaimServiceImpl implements IClaimService {

	@Autowired
	private ClaimRepository claimRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private InsuranceCompanyRepository insuranceCompanyRepository;

	@Override
	public ClaimDTO submitClaim(ClaimDTO claimDTO) {

		Patient patient = patientRepository.findById(claimDTO.getPatientId()).orElseThrow(
				() -> new PatientNotFoundException("Patient not found with ID: " + claimDTO.getPatientId()));

		Invoice invoice = invoiceRepository.findById(claimDTO.getInvoiceId()).orElseThrow(
				() -> new InvoiceNotFoundException("Invoice not found with ID: " + claimDTO.getInvoiceId()));

		InsuranceCompany company = insuranceCompanyRepository.findById(claimDTO.getCompanyId())
				.orElseThrow(() -> new InsuranceCompanyNotFoundException(
						"Insurance Company not found with ID: " + claimDTO.getCompanyId()));

		Claim claim = new Claim();

		claim.setPatient(patient);
		claim.setInvoice(invoice);
		claim.setCompany(company);
		claim.setClaimAmount(claimDTO.getClaimAmount());
		claim.setDiagnosis(claimDTO.getDiagnosis());
		claim.setTreatmentDetails(claimDTO.getTreatmentDetails());
		claim.setClaimDate(claimDTO.getClaimDate());
		claim.setStatus(claimDTO.getStatus());
		claim.setApprovedDate(claimDTO.getApprovedDate());
		claim.setRejectionReason(claimDTO.getRejectionReason());

		Claim savedClaim = claimRepository.save(claim);

		ClaimDTO dto = new ClaimDTO();

		dto.setClaimId(savedClaim.getClaimId());
		dto.setPatientId(savedClaim.getPatient().getPatientId());
		dto.setInvoiceId(savedClaim.getInvoice().getInvoiceId());
		dto.setCompanyId(savedClaim.getCompany().getCompanyId());
		dto.setClaimAmount(savedClaim.getClaimAmount());
		dto.setDiagnosis(savedClaim.getDiagnosis());
		dto.setTreatmentDetails(savedClaim.getTreatmentDetails());
		dto.setClaimDate(savedClaim.getClaimDate());
		dto.setStatus(savedClaim.getStatus());
		dto.setApprovedDate(savedClaim.getApprovedDate());
		dto.setRejectionReason(savedClaim.getRejectionReason());

		return dto;
	}

	@Override
	public ClaimDTO getClaimById(Integer claimId) {

		Claim claim = claimRepository.findById(claimId)
				.orElseThrow(() -> new ClaimNotFoundException("Claim not found with ID: " + claimId));

		ClaimDTO dto = new ClaimDTO();

		dto.setClaimId(claim.getClaimId());
		dto.setPatientId(claim.getPatient().getPatientId());
		dto.setInvoiceId(claim.getInvoice().getInvoiceId());
		dto.setCompanyId(claim.getCompany().getCompanyId());
		dto.setClaimAmount(claim.getClaimAmount());
		dto.setDiagnosis(claim.getDiagnosis());
		dto.setTreatmentDetails(claim.getTreatmentDetails());
		dto.setClaimDate(claim.getClaimDate());
		dto.setStatus(claim.getStatus());
		dto.setApprovedDate(claim.getApprovedDate());
		dto.setRejectionReason(claim.getRejectionReason());

		return dto;
	}

	@Override
	public List<ClaimDTO> getAllClaims() {

		List<Claim> claims = claimRepository.findAll();

		List<ClaimDTO> dtoList = new ArrayList<>();

		for (Claim claim : claims) {

			ClaimDTO dto = new ClaimDTO();

			dto.setClaimId(claim.getClaimId());
			dto.setPatientId(claim.getPatient().getPatientId());
			dto.setInvoiceId(claim.getInvoice().getInvoiceId());
			dto.setCompanyId(claim.getCompany().getCompanyId());
			dto.setClaimAmount(claim.getClaimAmount());
			dto.setDiagnosis(claim.getDiagnosis());
			dto.setTreatmentDetails(claim.getTreatmentDetails());
			dto.setClaimDate(claim.getClaimDate());
			dto.setStatus(claim.getStatus());
			dto.setApprovedDate(claim.getApprovedDate());
			dto.setRejectionReason(claim.getRejectionReason());

			dtoList.add(dto);
		}

		return dtoList;
	}

	@Override
	public ClaimDTO approveClaim(Integer claimId) {

		Claim claim = claimRepository.findById(claimId)
				.orElseThrow(() -> new ClaimNotFoundException("Claim not found with ID: " + claimId));

		claim.setStatus("APPROVED");

		Claim updatedClaim = claimRepository.save(claim);

		ClaimDTO dto = new ClaimDTO();

		dto.setClaimId(updatedClaim.getClaimId());
		dto.setPatientId(updatedClaim.getPatient().getPatientId());
		dto.setInvoiceId(updatedClaim.getInvoice().getInvoiceId());
		dto.setCompanyId(updatedClaim.getCompany().getCompanyId());
		dto.setClaimAmount(updatedClaim.getClaimAmount());
		dto.setDiagnosis(updatedClaim.getDiagnosis());
		dto.setTreatmentDetails(updatedClaim.getTreatmentDetails());
		dto.setClaimDate(updatedClaim.getClaimDate());
		dto.setStatus(updatedClaim.getStatus());
		dto.setApprovedDate(updatedClaim.getApprovedDate());
		dto.setRejectionReason(updatedClaim.getRejectionReason());

		return dto;
	}

	@Override
	public ClaimDTO rejectClaim(Integer claimId, String reason) {

		Claim claim = claimRepository.findById(claimId)
				.orElseThrow(() -> new ClaimNotFoundException("Claim not found with ID: " + claimId));

		claim.setStatus("REJECTED");
		claim.setRejectionReason(reason);

		Claim updatedClaim = claimRepository.save(claim);

		ClaimDTO dto = new ClaimDTO();

		dto.setClaimId(updatedClaim.getClaimId());
		dto.setPatientId(updatedClaim.getPatient().getPatientId());
		dto.setInvoiceId(updatedClaim.getInvoice().getInvoiceId());
		dto.setCompanyId(updatedClaim.getCompany().getCompanyId());
		dto.setClaimAmount(updatedClaim.getClaimAmount());
		dto.setDiagnosis(updatedClaim.getDiagnosis());
		dto.setTreatmentDetails(updatedClaim.getTreatmentDetails());
		dto.setClaimDate(updatedClaim.getClaimDate());
		dto.setStatus(updatedClaim.getStatus());
		dto.setApprovedDate(updatedClaim.getApprovedDate());
		dto.setRejectionReason(updatedClaim.getRejectionReason());

		return dto;
	}

}