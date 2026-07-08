package com.hexaware.careassist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careassist.dto.InsuranceCompanyDTO;
import com.hexaware.careassist.entity.InsuranceCompany;
import com.hexaware.careassist.entity.User;
import com.hexaware.careassist.exception.InsuranceCompanyNotFoundException;
import com.hexaware.careassist.exception.UserNotFoundException;
import com.hexaware.careassist.repository.InsuranceCompanyRepository;
import com.hexaware.careassist.repository.UserRepository;
import com.hexaware.careassist.service.IInsuranceCompanyService;

@Service
public class InsuranceCompanyServiceImpl implements IInsuranceCompanyService {

	private static final Logger logger = LoggerFactory.getLogger(InsuranceCompanyServiceImpl.class);

	@Autowired
	private InsuranceCompanyRepository insuranceCompanyRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public InsuranceCompanyDTO addCompany(InsuranceCompanyDTO companyDTO) {

		logger.info("Adding insurance company for user id {}", companyDTO.getUserId());

		User user = userRepository.findById(companyDTO.getUserId()).orElseThrow(() -> {

			logger.warn("User not found with id {}", companyDTO.getUserId());

			return new UserNotFoundException("User not found with ID: " + companyDTO.getUserId());
		});

		InsuranceCompany company = new InsuranceCompany();

		company.setUser(user);
		company.setCompanyName(companyDTO.getCompanyName());
		company.setLicenseNumber(companyDTO.getLicenseNumber());
		company.setAddress(companyDTO.getAddress());

		InsuranceCompany savedCompany = insuranceCompanyRepository.save(company);

		logger.info("Insurance company added successfully with id {}", savedCompany.getCompanyId());

		InsuranceCompanyDTO responseDTO = new InsuranceCompanyDTO();

		responseDTO.setCompanyId(savedCompany.getCompanyId());
		responseDTO.setUserId(savedCompany.getUser().getUserId());
		responseDTO.setCompanyName(savedCompany.getCompanyName());
		responseDTO.setLicenseNumber(savedCompany.getLicenseNumber());
		responseDTO.setAddress(savedCompany.getAddress());

		return responseDTO;
	}

	@Override
	public InsuranceCompanyDTO getCompanyById(Integer companyId) {

		logger.info("Fetching insurance company with id {}", companyId);

		InsuranceCompany company = insuranceCompanyRepository.findById(companyId).orElseThrow(() -> {

			logger.warn("Insurance company not found with id {}", companyId);

			return new InsuranceCompanyNotFoundException("Insurance Company not found with ID: " + companyId);
		});

		InsuranceCompanyDTO companyDTO = new InsuranceCompanyDTO();

		companyDTO.setCompanyId(company.getCompanyId());
		companyDTO.setUserId(company.getUser().getUserId());
		companyDTO.setCompanyName(company.getCompanyName());
		companyDTO.setLicenseNumber(company.getLicenseNumber());
		companyDTO.setAddress(company.getAddress());

		return companyDTO;
	}

	@Override
	public List<InsuranceCompanyDTO> getAllCompanies() {

		logger.info("Fetching all insurance companies");

		List<InsuranceCompany> companies = insuranceCompanyRepository.findAll();

		List<InsuranceCompanyDTO> companyDTOList = new ArrayList<>();

		for (InsuranceCompany company : companies) {

			InsuranceCompanyDTO companyDTO = new InsuranceCompanyDTO();

			companyDTO.setCompanyId(company.getCompanyId());
			companyDTO.setUserId(company.getUser().getUserId());
			companyDTO.setCompanyName(company.getCompanyName());
			companyDTO.setLicenseNumber(company.getLicenseNumber());
			companyDTO.setAddress(company.getAddress());

			companyDTOList.add(companyDTO);
		}

		logger.info("Total insurance companies fetched: {}", companyDTOList.size());

		return companyDTOList;
	}

	@Override
	public InsuranceCompanyDTO updateCompany(Integer companyId, InsuranceCompanyDTO companyDTO) {

		logger.info("Updating insurance company with id {}", companyId);

		InsuranceCompany company = insuranceCompanyRepository.findById(companyId).orElseThrow(() -> {

			logger.warn("Insurance company not found with id {}", companyId);

			return new InsuranceCompanyNotFoundException("Insurance Company not found with ID: " + companyId);
		});

		User user = userRepository.findById(companyDTO.getUserId()).orElseThrow(() -> {

			logger.warn("User not found with id {}", companyDTO.getUserId());

			return new UserNotFoundException("User not found with ID: " + companyDTO.getUserId());
		});

		company.setUser(user);
		company.setCompanyName(companyDTO.getCompanyName());
		company.setLicenseNumber(companyDTO.getLicenseNumber());
		company.setAddress(companyDTO.getAddress());

		InsuranceCompany updatedCompany = insuranceCompanyRepository.save(company);

		logger.info("Insurance company updated successfully with id {}", updatedCompany.getCompanyId());

		InsuranceCompanyDTO responseDTO = new InsuranceCompanyDTO();

		responseDTO.setCompanyId(updatedCompany.getCompanyId());
		responseDTO.setUserId(updatedCompany.getUser().getUserId());
		responseDTO.setCompanyName(updatedCompany.getCompanyName());
		responseDTO.setLicenseNumber(updatedCompany.getLicenseNumber());
		responseDTO.setAddress(updatedCompany.getAddress());

		return responseDTO;
	}

	@Override
	public void deleteCompany(Integer companyId) {

		logger.info("Deleting insurance company with id {}", companyId);

		InsuranceCompany company = insuranceCompanyRepository.findById(companyId).orElseThrow(() -> {

			logger.warn("Insurance company not found with id {}", companyId);

			return new InsuranceCompanyNotFoundException("Insurance Company not found with ID: " + companyId);
		});

		insuranceCompanyRepository.delete(company);

		logger.info("Insurance company deleted successfully with id {}", companyId);
	}
}