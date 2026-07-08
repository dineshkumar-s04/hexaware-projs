package com.hexaware.careassist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careassist.dto.InsurancePlanDTO;
import com.hexaware.careassist.entity.InsuranceCompany;
import com.hexaware.careassist.entity.InsurancePlan;
import com.hexaware.careassist.exception.InsuranceCompanyNotFoundException;
import com.hexaware.careassist.exception.InsurancePlanNotFoundException;
import com.hexaware.careassist.repository.InsuranceCompanyRepository;
import com.hexaware.careassist.repository.InsurancePlanRepository;
import com.hexaware.careassist.service.IInsurancePlanService;

@Service
public class InsurancePlanServiceImpl implements IInsurancePlanService {

	private static final Logger logger = LoggerFactory.getLogger(InsurancePlanServiceImpl.class);

	@Autowired
	private InsurancePlanRepository insurancePlanRepository;

	@Autowired
	private InsuranceCompanyRepository insuranceCompanyRepository;

	@Override
	public InsurancePlanDTO addPlan(InsurancePlanDTO planDTO) {

		logger.info("Adding insurance plan for company id {}", planDTO.getCompanyId());

		InsuranceCompany company = insuranceCompanyRepository.findById(planDTO.getCompanyId()).orElseThrow(() -> {

			logger.warn("Insurance company not found with id {}", planDTO.getCompanyId());

			return new InsuranceCompanyNotFoundException(
					"Insurance Company not found with ID: " + planDTO.getCompanyId());
		});

		InsurancePlan plan = new InsurancePlan();

		plan.setCompany(company);
		plan.setPlanName(planDTO.getPlanName());
		plan.setCoverageAmount(planDTO.getCoverageAmount());
		plan.setPremium(planDTO.getPremium());
		plan.setDescription(planDTO.getDescription());

		InsurancePlan savedPlan = insurancePlanRepository.save(plan);

		logger.info("Insurance plan added successfully with id {}", savedPlan.getPlanId());

		InsurancePlanDTO responseDTO = new InsurancePlanDTO();

		responseDTO.setPlanId(savedPlan.getPlanId());
		responseDTO.setCompanyId(savedPlan.getCompany().getCompanyId());
		responseDTO.setPlanName(savedPlan.getPlanName());
		responseDTO.setCoverageAmount(savedPlan.getCoverageAmount());
		responseDTO.setPremium(savedPlan.getPremium());
		responseDTO.setDescription(savedPlan.getDescription());

		return responseDTO;
	}

	@Override
	public InsurancePlanDTO getPlanById(Integer planId) {

		logger.info("Fetching insurance plan with id {}", planId);

		InsurancePlan plan = insurancePlanRepository.findById(planId).orElseThrow(() -> {

			logger.warn("Insurance plan not found with id {}", planId);

			return new InsurancePlanNotFoundException("Insurance Plan not found with ID: " + planId);
		});

		InsurancePlanDTO planDTO = new InsurancePlanDTO();

		planDTO.setPlanId(plan.getPlanId());
		planDTO.setCompanyId(plan.getCompany().getCompanyId());
		planDTO.setPlanName(plan.getPlanName());
		planDTO.setCoverageAmount(plan.getCoverageAmount());
		planDTO.setPremium(plan.getPremium());
		planDTO.setDescription(plan.getDescription());

		return planDTO;
	}

	@Override
	public List<InsurancePlanDTO> getAllPlans() {

		logger.info("Fetching all insurance plans");

		List<InsurancePlan> plans = insurancePlanRepository.findAll();

		List<InsurancePlanDTO> planDTOList = new ArrayList<>();

		for (InsurancePlan plan : plans) {

			InsurancePlanDTO planDTO = new InsurancePlanDTO();

			planDTO.setPlanId(plan.getPlanId());
			planDTO.setCompanyId(plan.getCompany().getCompanyId());
			planDTO.setPlanName(plan.getPlanName());
			planDTO.setCoverageAmount(plan.getCoverageAmount());
			planDTO.setPremium(plan.getPremium());
			planDTO.setDescription(plan.getDescription());

			planDTOList.add(planDTO);
		}

		logger.info("Total insurance plans fetched: {}", planDTOList.size());

		return planDTOList;
	}

	@Override
	public InsurancePlanDTO updatePlan(Integer planId, InsurancePlanDTO planDTO) {

		logger.info("Updating insurance plan with id {}", planId);

		InsurancePlan plan = insurancePlanRepository.findById(planId).orElseThrow(() -> {

			logger.warn("Insurance plan not found with id {}", planId);

			return new InsurancePlanNotFoundException("Insurance Plan not found with ID: " + planId);
		});

		InsuranceCompany company = insuranceCompanyRepository.findById(planDTO.getCompanyId()).orElseThrow(() -> {

			logger.warn("Insurance company not found with id {}", planDTO.getCompanyId());

			return new InsuranceCompanyNotFoundException(
					"Insurance Company not found with ID: " + planDTO.getCompanyId());
		});

		plan.setCompany(company);
		plan.setPlanName(planDTO.getPlanName());
		plan.setCoverageAmount(planDTO.getCoverageAmount());
		plan.setPremium(planDTO.getPremium());
		plan.setDescription(planDTO.getDescription());

		InsurancePlan updatedPlan = insurancePlanRepository.save(plan);

		logger.info("Insurance plan updated successfully with id {}", updatedPlan.getPlanId());

		InsurancePlanDTO responseDTO = new InsurancePlanDTO();

		responseDTO.setPlanId(updatedPlan.getPlanId());
		responseDTO.setCompanyId(updatedPlan.getCompany().getCompanyId());
		responseDTO.setPlanName(updatedPlan.getPlanName());
		responseDTO.setCoverageAmount(updatedPlan.getCoverageAmount());
		responseDTO.setPremium(updatedPlan.getPremium());
		responseDTO.setDescription(updatedPlan.getDescription());

		return responseDTO;
	}

	@Override
	public void deletePlan(Integer planId) {

		logger.info("Deleting insurance plan with id {}", planId);

		InsurancePlan plan = insurancePlanRepository.findById(planId).orElseThrow(() -> {

			logger.warn("Insurance plan not found with id {}", planId);

			return new InsurancePlanNotFoundException("Insurance Plan not found with ID: " + planId);
		});

		insurancePlanRepository.delete(plan);

		logger.info("Insurance plan deleted successfully with id {}", planId);
	}
}