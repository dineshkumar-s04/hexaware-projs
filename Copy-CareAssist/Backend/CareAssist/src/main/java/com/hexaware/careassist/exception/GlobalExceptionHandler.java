package com.hexaware.careassist.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger =
	        LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {

		logger.error("Exception occurred: {}", ex.getMessage());
		
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PatientNotFoundException.class)
	public ResponseEntity<String> handlePatientNotFoundException(PatientNotFoundException ex) {

		logger.error("Exception occurred: {}", ex.getMessage());
		
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ProviderNotFoundException.class)
	public ResponseEntity<String> handleProviderNotFoundException(ProviderNotFoundException ex) {

		logger.error("Exception occurred: {}", ex.getMessage());
		
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InsuranceCompanyNotFoundException.class)
	public ResponseEntity<String> handleInsuranceCompanyNotFoundException(InsuranceCompanyNotFoundException ex) {

		logger.error("Exception occurred: {}", ex.getMessage());
		
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InsurancePlanNotFoundException.class)
	public ResponseEntity<String> handleInsurancePlanNotFoundException(InsurancePlanNotFoundException ex) {

		logger.error("Exception occurred: {}", ex.getMessage());
		
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PatientInsuranceNotFoundException.class)
	public ResponseEntity<String> handlePatientInsuranceNotFoundException(PatientInsuranceNotFoundException ex) {

		logger.error("Exception occurred: {}", ex.getMessage());
		
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvoiceNotFoundException.class)
	public ResponseEntity<String> handleInvoiceNotFoundException(InvoiceNotFoundException ex) {

		logger.error("Exception occurred: {}", ex.getMessage());
		
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ClaimNotFoundException.class)
	public ResponseEntity<String> handleClaimNotFoundException(ClaimNotFoundException ex) {

		logger.error("Exception occurred: {}", ex.getMessage());
		
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PaymentNotFoundException.class)
	public ResponseEntity<String> handlePaymentNotFoundException(PaymentNotFoundException ex) {

		logger.error("Exception occurred: {}", ex.getMessage());
		
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NotificationNotFoundException.class)
	public ResponseEntity<String> handleNotificationNotFoundException(NotificationNotFoundException ex) {

		logger.error("Exception occurred: {}", ex.getMessage());
		
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception ex) {

	    logger.error("Unexpected error occurred", ex);

	    return new ResponseEntity<>(
	            "Something went wrong. Please try again later.",
	            HttpStatus.INTERNAL_SERVER_ERROR);
	}
}