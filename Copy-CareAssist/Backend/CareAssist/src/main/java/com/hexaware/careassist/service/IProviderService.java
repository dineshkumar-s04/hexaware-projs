package com.hexaware.careassist.service;

import java.util.List;
import com.hexaware.careassist.dto.ProviderDTO;

public interface IProviderService {

    ProviderDTO addProvider(ProviderDTO providerDTO);

    ProviderDTO getProviderById(Integer providerId);

    List<ProviderDTO> getAllProviders();

    ProviderDTO updateProvider(Integer providerId,
                               ProviderDTO providerDTO);

    void deleteProvider(Integer providerId);
    
    ProviderDTO getProviderByEmail(String email);
}