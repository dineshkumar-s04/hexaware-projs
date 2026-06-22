package com.hexaware.careassist.service.impl;

import java.util.ArrayList;
import java.util.List;

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

	@Autowired
	private InsurancePlanRepository insurancePlanRepository;

	@Autowired
	private InsuranceCompanyRepository insuranceCompanyRepository;

	@Override
	public InsurancePlanDTO addPlan(InsurancePlanDTO planDTO) {

		InsuranceCompany company = insuranceCompanyRepository.findById(planDTO.getCompanyId())
				.orElseThrow(() -> new InsuranceCompanyNotFoundException(
						"Insurance Company not found with ID: " + planDTO.getCompanyId()));

		InsurancePlan plan = new InsurancePlan();

		plan.setCompany(company);
		plan.setPlanName(planDTO.getPlanName());
		plan.setCoverageAmount(planDTO.getCoverageAmount());
		plan.setPremium(planDTO.getPremium());
		plan.setDescription(planDTO.getDescription());

		InsurancePlan savedPlan = insurancePlanRepository.save(plan);

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

		InsurancePlan plan = insurancePlanRepository.findById(planId)
				.orElseThrow(() -> new InsurancePlanNotFoundException("Insurance Plan not found with ID: " + planId));

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

		return planDTOList;
	}

	@Override
	public InsurancePlanDTO updatePlan(Integer planId, InsurancePlanDTO planDTO) {

		InsurancePlan plan = insurancePlanRepository.findById(planId)
				.orElseThrow(() -> new InsurancePlanNotFoundException("Insurance Plan not found with ID: " + planId));

		InsuranceCompany company = insuranceCompanyRepository.findById(planDTO.getCompanyId())
				.orElseThrow(() -> new InsuranceCompanyNotFoundException(
						"Insurance Company not found with ID: " + planDTO.getCompanyId()));

		plan.setCompany(company);
		plan.setPlanName(planDTO.getPlanName());
		plan.setCoverageAmount(planDTO.getCoverageAmount());
		plan.setPremium(planDTO.getPremium());
		plan.setDescription(planDTO.getDescription());

		InsurancePlan updatedPlan = insurancePlanRepository.save(plan);

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

		InsurancePlan plan = insurancePlanRepository.findById(planId)
				.orElseThrow(() -> new InsurancePlanNotFoundException("Insurance Plan not found with ID: " + planId));

		insurancePlanRepository.delete(plan);
	}

}