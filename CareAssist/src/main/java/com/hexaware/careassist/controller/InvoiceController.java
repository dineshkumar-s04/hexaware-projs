package com.hexaware.careassist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.careassist.dto.InvoiceDTO;
import com.hexaware.careassist.service.IInvoiceService;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

	@Autowired
	private IInvoiceService invoiceService;

	@PostMapping
	public ResponseEntity<InvoiceDTO> generateInvoice(@RequestBody InvoiceDTO invoiceDTO) {

		InvoiceDTO savedInvoice = invoiceService.generateInvoice(invoiceDTO);

		return new ResponseEntity<>(savedInvoice, HttpStatus.CREATED);
	}

	@GetMapping("/{invoiceId}")
	public ResponseEntity<InvoiceDTO> getInvoiceById(@PathVariable Integer invoiceId) {

		InvoiceDTO invoice = invoiceService.getInvoiceById(invoiceId);

		return ResponseEntity.ok(invoice);
	}

	@GetMapping
	public ResponseEntity<List<InvoiceDTO>> getAllInvoices() {

		List<InvoiceDTO> invoices = invoiceService.getAllInvoices();

		return ResponseEntity.ok(invoices);
	}

	@PutMapping("/{invoiceId}/{status}")
	public ResponseEntity<InvoiceDTO> updateInvoiceStatus(@PathVariable Integer invoiceId,
			@PathVariable String status) {

		InvoiceDTO updatedInvoice = invoiceService.updateInvoiceStatus(invoiceId, status);

		return ResponseEntity.ok(updatedInvoice);
	}

	@DeleteMapping("/{invoiceId}")
	public ResponseEntity<String> deleteInvoice(@PathVariable Integer invoiceId) {

		invoiceService.deleteInvoice(invoiceId);

		return ResponseEntity.ok("Invoice deleted successfully");
	}
}