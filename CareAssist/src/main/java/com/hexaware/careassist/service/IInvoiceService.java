package com.hexaware.careassist.service;

import java.util.List;
import com.hexaware.careassist.dto.InvoiceDTO;

public interface IInvoiceService {

    InvoiceDTO generateInvoice(
            InvoiceDTO invoiceDTO);

    InvoiceDTO getInvoiceById(
            Integer invoiceId);

    List<InvoiceDTO> getAllInvoices();
    
    List<InvoiceDTO> getInvoicesByPatientId(
            Integer patientId);

    InvoiceDTO updateInvoiceStatus(
            Integer invoiceId,
            String status);

    void deleteInvoice(Integer invoiceId);
    
    List<InvoiceDTO> getInvoicesByProviderId(Integer providerId);
}