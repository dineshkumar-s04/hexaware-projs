package com.hexaware.careassist.service;

import java.util.List;
import com.hexaware.careassist.dto.InsurancePlanDTO;

public interface IInsurancePlanService {

    InsurancePlanDTO addPlan(
            InsurancePlanDTO planDTO);

    InsurancePlanDTO getPlanById(
            Integer planId);

    List<InsurancePlanDTO> getAllPlans();

    InsurancePlanDTO updatePlan(
            Integer planId,
            InsurancePlanDTO planDTO);

    void deletePlan(Integer planId);
}