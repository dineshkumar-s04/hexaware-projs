package com.hexaware.careassist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.careassist.entity.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
	List<Invoice> findByPatientPatientId(Integer patientId);
	List<Invoice> findByProviderProviderId(Integer providerId);
	
}