package com.hexaware.careassist.service;

import java.util.List;
import com.hexaware.careassist.dto.InsuranceCompanyDTO;

public interface IInsuranceCompanyService {

    InsuranceCompanyDTO addCompany(
            InsuranceCompanyDTO companyDTO);

    InsuranceCompanyDTO getCompanyById(
            Integer companyId);

    List<InsuranceCompanyDTO> getAllCompanies();

    InsuranceCompanyDTO updateCompany(
            Integer companyId,
            InsuranceCompanyDTO companyDTO);

    void deleteCompany(Integer companyId);
}