package com.hexaware.careassist.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careassist.dto.InvoiceDTO;
import com.hexaware.careassist.entity.Invoice;
import com.hexaware.careassist.entity.Patient;
import com.hexaware.careassist.entity.Provider;
import com.hexaware.careassist.exception.InvoiceNotFoundException;
import com.hexaware.careassist.exception.PatientNotFoundException;
import com.hexaware.careassist.exception.ProviderNotFoundException;
import com.hexaware.careassist.repository.InvoiceRepository;
import com.hexaware.careassist.repository.PatientRepository;
import com.hexaware.careassist.repository.ProviderRepository;
import com.hexaware.careassist.service.IInvoiceService;

@Service
public class InvoiceServiceImpl implements IInvoiceService {

	private static final Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private ProviderRepository providerRepository;

	@Override
	public InvoiceDTO generateInvoice(InvoiceDTO invoiceDTO) {

		logger.info("Generating invoice for patient id {} and provider id {}", invoiceDTO.getPatientId(),
				invoiceDTO.getProviderId());

		Patient patient = patientRepository.findById(invoiceDTO.getPatientId()).orElseThrow(() -> {

			logger.warn("Patient not found with id {}", invoiceDTO.getPatientId());

			return new PatientNotFoundException("Patient not found with ID: " + invoiceDTO.getPatientId());
		});

		Provider provider = providerRepository.findById(invoiceDTO.getProviderId()).orElseThrow(() -> {

			logger.warn("Provider not found with id {}", invoiceDTO.getProviderId());

			return new ProviderNotFoundException("Provider not found with ID: " + invoiceDTO.getProviderId());
		});

		Invoice invoice = new Invoice();

		invoice.setInvoiceNumber("INV-" + System.currentTimeMillis());
		invoice.setPatient(patient);
		invoice.setProvider(provider);
		invoice.setConsultationFee(invoiceDTO.getConsultationFee());
		invoice.setDiagnosticTestFee(invoiceDTO.getDiagnosticTestFee());
		invoice.setScanFee(invoiceDTO.getScanFee());
		invoice.setMedicineFee(invoiceDTO.getMedicineFee());

		BigDecimal billAmount = invoiceDTO.getConsultationFee()
		        .add(invoiceDTO.getDiagnosticTestFee())
		        .add(invoiceDTO.getScanFee())
		        .add(invoiceDTO.getMedicineFee());

		BigDecimal tax = billAmount.multiply(new BigDecimal("0.08"));

		BigDecimal totalAmount = billAmount.add(tax);

		invoice.setTax(tax);
		invoice.setTotalAmount(totalAmount);
		invoice.setStatus("PENDING");
		invoice.setInvoiceDate(invoiceDTO.getInvoiceDate());
		invoice.setDueDate(invoiceDTO.getDueDate());

		Invoice savedInvoice = invoiceRepository.save(invoice);

		logger.info("Invoice generated successfully with id {}", savedInvoice.getInvoiceId());

		InvoiceDTO responseDTO = new InvoiceDTO();

		responseDTO.setInvoiceId(savedInvoice.getInvoiceId());
		responseDTO.setInvoiceNumber(savedInvoice.getInvoiceNumber());
		responseDTO.setPatientId(savedInvoice.getPatient().getPatientId());
		responseDTO.setProviderId(savedInvoice.getProvider().getProviderId());
		responseDTO.setConsultationFee(savedInvoice.getConsultationFee());
		responseDTO.setDiagnosticTestFee(savedInvoice.getDiagnosticTestFee());
		responseDTO.setScanFee(savedInvoice.getScanFee());
		responseDTO.setMedicineFee(savedInvoice.getMedicineFee());
		responseDTO.setTax(savedInvoice.getTax());
		responseDTO.setTotalAmount(savedInvoice.getTotalAmount());
		responseDTO.setStatus(savedInvoice.getStatus());
		responseDTO.setInvoiceDate(savedInvoice.getInvoiceDate());
		responseDTO.setDueDate(savedInvoice.getDueDate());

		return responseDTO;
	}

	@Override
	public InvoiceDTO getInvoiceById(Integer invoiceId) {

		logger.info("Fetching invoice with id {}", invoiceId);

		Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> {

			logger.warn("Invoice not found with id {}", invoiceId);

			return new InvoiceNotFoundException("Invoice not found with ID: " + invoiceId);
		});

		InvoiceDTO dto = new InvoiceDTO();

		dto.setInvoiceId(invoice.getInvoiceId());
		dto.setInvoiceNumber(invoice.getInvoiceNumber());
		dto.setPatientId(invoice.getPatient().getPatientId());
		dto.setProviderId(invoice.getProvider().getProviderId());
		dto.setConsultationFee(invoice.getConsultationFee());
		dto.setDiagnosticTestFee(invoice.getDiagnosticTestFee());
		dto.setScanFee(invoice.getScanFee());
		dto.setMedicineFee(invoice.getMedicineFee());
		dto.setTax(invoice.getTax());
		dto.setTotalAmount(invoice.getTotalAmount());
		dto.setStatus(invoice.getStatus());
		dto.setInvoiceDate(invoice.getInvoiceDate());
		dto.setDueDate(invoice.getDueDate());

		return dto;
	}

	@Override
	public List<InvoiceDTO> getAllInvoices() {

		logger.info("Fetching all invoices");

		List<Invoice> invoices = invoiceRepository.findAll();

		List<InvoiceDTO> dtoList = new ArrayList<>();

		for (Invoice invoice : invoices) {

			InvoiceDTO dto = new InvoiceDTO();

			dto.setInvoiceId(invoice.getInvoiceId());
			dto.setInvoiceNumber(invoice.getInvoiceNumber());
			dto.setPatientId(invoice.getPatient().getPatientId());
			dto.setProviderId(invoice.getProvider().getProviderId());
			dto.setConsultationFee(invoice.getConsultationFee());
			dto.setDiagnosticTestFee(invoice.getDiagnosticTestFee());
			dto.setScanFee(invoice.getScanFee());
			dto.setMedicineFee(invoice.getMedicineFee());
			dto.setTax(invoice.getTax());
			dto.setTotalAmount(invoice.getTotalAmount());
			dto.setStatus(invoice.getStatus());
			dto.setInvoiceDate(invoice.getInvoiceDate());
			dto.setDueDate(invoice.getDueDate());

			dtoList.add(dto);
		}

		logger.info("Total invoices fetched: {}", dtoList.size());

		return dtoList;
	}
	
	@Override
	public List<InvoiceDTO> getInvoicesByPatientId(Integer patientId) {

	    logger.info("Fetching invoices for patient {}", patientId);

	    List<Invoice> invoices =
	            invoiceRepository.findByPatientPatientId(patientId);

	    List<InvoiceDTO> dtoList = new ArrayList<>();

	    for (Invoice invoice : invoices) {

	        InvoiceDTO dto = new InvoiceDTO();

	        dto.setInvoiceId(invoice.getInvoiceId());
	        dto.setInvoiceNumber(invoice.getInvoiceNumber());
	        dto.setPatientId(invoice.getPatient().getPatientId());
	        dto.setProviderId(invoice.getProvider().getProviderId());
	        dto.setConsultationFee(invoice.getConsultationFee());
	        dto.setDiagnosticTestFee(invoice.getDiagnosticTestFee());
	        dto.setScanFee(invoice.getScanFee());
	        dto.setMedicineFee(invoice.getMedicineFee());
	        dto.setTax(invoice.getTax());
	        dto.setTotalAmount(invoice.getTotalAmount());
	        dto.setStatus(invoice.getStatus());
	        dto.setInvoiceDate(invoice.getInvoiceDate());
	        dto.setDueDate(invoice.getDueDate());

	        dtoList.add(dto);
	    }

	    logger.info("Total invoices found for patient {}: {}", patientId, dtoList.size());

	    return dtoList;
	}

	@Override
	public InvoiceDTO updateInvoiceStatus(Integer invoiceId, String status) {

		logger.info("Updating invoice status for invoice id {}", invoiceId);

		Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> {

			logger.warn("Invoice not found with id {}", invoiceId);

			return new InvoiceNotFoundException("Invoice not found with ID: " + invoiceId);
		});

		invoice.setStatus(status);

		Invoice updatedInvoice = invoiceRepository.save(invoice);

		logger.info("Invoice status updated successfully for invoice id {}", updatedInvoice.getInvoiceId());

		InvoiceDTO dto = new InvoiceDTO();

		dto.setInvoiceId(updatedInvoice.getInvoiceId());
		dto.setInvoiceNumber(updatedInvoice.getInvoiceNumber());
		dto.setPatientId(updatedInvoice.getPatient().getPatientId());
		dto.setProviderId(updatedInvoice.getProvider().getProviderId());
		dto.setConsultationFee(updatedInvoice.getConsultationFee());
		dto.setDiagnosticTestFee(updatedInvoice.getDiagnosticTestFee());
		dto.setScanFee(updatedInvoice.getScanFee());
		dto.setMedicineFee(updatedInvoice.getMedicineFee());
		dto.setTax(updatedInvoice.getTax());
		dto.setTotalAmount(updatedInvoice.getTotalAmount());
		dto.setStatus(updatedInvoice.getStatus());
		dto.setInvoiceDate(updatedInvoice.getInvoiceDate());
		dto.setDueDate(updatedInvoice.getDueDate());

		return dto;
	}
	
	@Override
	public List<InvoiceDTO> getInvoicesByProviderId(Integer providerId) {

	    logger.info("Fetching invoices for provider {}", providerId);

	    List<Invoice> invoices =
	            invoiceRepository.findByProviderProviderId(providerId);

	    List<InvoiceDTO> dtoList = new ArrayList<>();

	    for (Invoice invoice : invoices) {

	        InvoiceDTO dto = new InvoiceDTO();

	        dto.setInvoiceId(invoice.getInvoiceId());
	        dto.setInvoiceNumber(invoice.getInvoiceNumber());
	        dto.setPatientId(invoice.getPatient().getPatientId());
	        dto.setProviderId(invoice.getProvider().getProviderId());
	        dto.setConsultationFee(invoice.getConsultationFee());
	        dto.setDiagnosticTestFee(invoice.getDiagnosticTestFee());
	        dto.setScanFee(invoice.getScanFee());
	        dto.setMedicineFee(invoice.getMedicineFee());
	        dto.setTax(invoice.getTax());
	        dto.setTotalAmount(invoice.getTotalAmount());
	        dto.setStatus(invoice.getStatus());
	        dto.setInvoiceDate(invoice.getInvoiceDate());
	        dto.setDueDate(invoice.getDueDate());

	        dtoList.add(dto);

	    }

	    logger.info("Total invoices found for provider {}: {}",
	            providerId,
	            dtoList.size());

	    return dtoList;

	}

	@Override
	public void deleteInvoice(Integer invoiceId) {

		logger.info("Deleting invoice with id {}", invoiceId);

		Invoice invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> {

			logger.warn("Invoice not found with id {}", invoiceId);

			return new InvoiceNotFoundException("Invoice not found with ID: " + invoiceId);
		});

		invoiceRepository.delete(invoice);

		logger.info("Invoice deleted successfully with id {}", invoiceId);
	}
}