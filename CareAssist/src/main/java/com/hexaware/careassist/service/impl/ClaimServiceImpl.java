package com.hexaware.careassist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careassist.dto.ClaimDTO;
import com.hexaware.careassist.dto.ClaimDocumentDTO;
import com.hexaware.careassist.entity.Claim;
import com.hexaware.careassist.entity.ClaimDocument;
import com.hexaware.careassist.entity.InsuranceCompany;
import com.hexaware.careassist.entity.Invoice;
import com.hexaware.careassist.entity.Patient;
import com.hexaware.careassist.exception.ClaimNotFoundException;
import com.hexaware.careassist.exception.InsuranceCompanyNotFoundException;
import com.hexaware.careassist.exception.InvoiceNotFoundException;
import com.hexaware.careassist.exception.PatientNotFoundException;
import com.hexaware.careassist.repository.ClaimDocumentRepository;
import com.hexaware.careassist.repository.ClaimRepository;
import com.hexaware.careassist.repository.InsuranceCompanyRepository;
import com.hexaware.careassist.repository.InvoiceRepository;
import com.hexaware.careassist.repository.PatientRepository;
import com.hexaware.careassist.service.IClaimService;

@Service
public class ClaimServiceImpl implements IClaimService {

	private static final Logger logger = LoggerFactory.getLogger(ClaimServiceImpl.class);

	@Autowired
	private ClaimRepository claimRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private InsuranceCompanyRepository insuranceCompanyRepository;

	@Autowired
	private ClaimDocumentRepository claimDocumentRepository;

	@Override
	public ClaimDTO submitClaim(ClaimDTO claimDTO) {

		logger.info("Submitting claim for patient id {}", claimDTO.getPatientId());

		Patient patient = patientRepository.findById(claimDTO.getPatientId()).orElseThrow(() -> {

			logger.warn("Patient not found with id {}", claimDTO.getPatientId());

			return new PatientNotFoundException("Patient not found with ID: " + claimDTO.getPatientId());
		});

		Invoice invoice = invoiceRepository.findById(claimDTO.getInvoiceId()).orElseThrow(() -> {

			logger.warn("Invoice not found with id {}", claimDTO.getInvoiceId());

			return new InvoiceNotFoundException("Invoice not found with ID: " + claimDTO.getInvoiceId());
		});

		InsuranceCompany company = insuranceCompanyRepository.findById(claimDTO.getCompanyId()).orElseThrow(() -> {

			logger.warn("Insurance company not found with id {}", claimDTO.getCompanyId());

			return new InsuranceCompanyNotFoundException(
					"Insurance Company not found with ID: " + claimDTO.getCompanyId());
		});

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

		if (claimDTO.getDocuments() != null && !claimDTO.getDocuments().isEmpty()) {

			for (ClaimDocumentDTO documentDTO : claimDTO.getDocuments()) {

				ClaimDocument document = new ClaimDocument();

				document.setClaim(savedClaim);
				document.setFileName(documentDTO.getFileName());
				document.setFilePath(documentDTO.getFilePath());

				claimDocumentRepository.save(document);
			}
		}

		logger.info("Claim submitted successfully with id {}", savedClaim.getClaimId());

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

		if (claimDTO.getDocuments() != null) {
			dto.setDocuments(claimDTO.getDocuments());
		}

		return dto;
	}

	@Override
	public ClaimDTO getClaimById(Integer claimId) {

		logger.info("Fetching claim with id {}", claimId);

		Claim claim = claimRepository.findById(claimId).orElseThrow(() -> {

			logger.warn("Claim not found with id {}", claimId);

			return new ClaimNotFoundException("Claim not found with ID: " + claimId);
		});

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

		List<ClaimDocument> documents = claimDocumentRepository.findByClaimClaimId(claim.getClaimId());

		List<ClaimDocumentDTO> documentDTOs = new ArrayList<>();

		for (ClaimDocument document : documents) {

			ClaimDocumentDTO documentDTO = new ClaimDocumentDTO();

			documentDTO.setDocumentId(document.getDocumentId());
			documentDTO.setFileName(document.getFileName());
			documentDTO.setFilePath(document.getFilePath());

			documentDTOs.add(documentDTO);
		}

		dto.setDocuments(documentDTOs);

		return dto;
	}

	@Override
	public List<ClaimDTO> getAllClaims() {

		logger.info("Fetching all claims");

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

			List<ClaimDocument> documents = claimDocumentRepository.findByClaimClaimId(claim.getClaimId());

			List<ClaimDocumentDTO> documentDTOs = new ArrayList<>();

			for (ClaimDocument document : documents) {

				ClaimDocumentDTO documentDTO = new ClaimDocumentDTO();

				documentDTO.setDocumentId(document.getDocumentId());
				documentDTO.setFileName(document.getFileName());
				documentDTO.setFilePath(document.getFilePath());

				documentDTOs.add(documentDTO);
			}

			dto.setDocuments(documentDTOs);

			dtoList.add(dto);
		}

		logger.info("Total claims fetched: {}", dtoList.size());

		return dtoList;
	}

	@Override
	public ClaimDTO approveClaim(Integer claimId) {

		logger.info("Approving claim with id {}", claimId);

		Claim claim = claimRepository.findById(claimId).orElseThrow(() -> {

			logger.warn("Claim not found with id {}", claimId);

			return new ClaimNotFoundException("Claim not found with ID: " + claimId);
		});

		claim.setStatus("APPROVED");

		Claim updatedClaim = claimRepository.save(claim);

		logger.info("Claim approved successfully with id {}", updatedClaim.getClaimId());

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

		List<ClaimDocument> documents = claimDocumentRepository.findByClaimClaimId(updatedClaim.getClaimId());

		List<ClaimDocumentDTO> documentDTOs = new ArrayList<>();

		for (ClaimDocument document : documents) {

			ClaimDocumentDTO documentDTO = new ClaimDocumentDTO();

			documentDTO.setDocumentId(document.getDocumentId());
			documentDTO.setFileName(document.getFileName());
			documentDTO.setFilePath(document.getFilePath());

			documentDTOs.add(documentDTO);
		}

		dto.setDocuments(documentDTOs);

		return dto;
	}

	@Override
	public ClaimDTO rejectClaim(Integer claimId, String reason) {

		logger.info("Rejecting claim with id {}", claimId);

		Claim claim = claimRepository.findById(claimId).orElseThrow(() -> {

			logger.warn("Claim not found with id {}", claimId);

			return new ClaimNotFoundException("Claim not found with ID: " + claimId);
		});

		claim.setStatus("REJECTED");
		claim.setRejectionReason(reason);

		Claim updatedClaim = claimRepository.save(claim);

		logger.info("Claim rejected successfully with id {}", updatedClaim.getClaimId());

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

		List<ClaimDocument> documents = claimDocumentRepository.findByClaimClaimId(updatedClaim.getClaimId());

		List<ClaimDocumentDTO> documentDTOs = new ArrayList<>();

		for (ClaimDocument document : documents) {

			ClaimDocumentDTO documentDTO = new ClaimDocumentDTO();

			documentDTO.setDocumentId(document.getDocumentId());
			documentDTO.setFileName(document.getFileName());
			documentDTO.setFilePath(document.getFilePath());

			documentDTOs.add(documentDTO);
		}

		dto.setDocuments(documentDTOs);

		return dto;
	}
}