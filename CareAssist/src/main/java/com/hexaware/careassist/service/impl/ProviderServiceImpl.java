package com.hexaware.careassist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careassist.dto.ProviderDTO;
import com.hexaware.careassist.entity.Provider;
import com.hexaware.careassist.entity.User;
import com.hexaware.careassist.exception.ProviderNotFoundException;
import com.hexaware.careassist.exception.UserNotFoundException;
import com.hexaware.careassist.repository.ProviderRepository;
import com.hexaware.careassist.repository.UserRepository;
import com.hexaware.careassist.service.IProviderService;

@Service
public class ProviderServiceImpl implements IProviderService {

	private static final Logger logger = LoggerFactory.getLogger(ProviderServiceImpl.class);

	@Autowired
	private ProviderRepository providerRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public ProviderDTO addProvider(ProviderDTO providerDTO) {

		logger.info("Adding provider for user id {}", providerDTO.getUserId());

		User user = userRepository.findById(providerDTO.getUserId()).orElseThrow(() -> {

			logger.warn("User not found with id {}", providerDTO.getUserId());

			return new UserNotFoundException("User not found with ID: " + providerDTO.getUserId());
		});

		Provider provider = new Provider();

		provider.setUser(user);
		provider.setHospitalName(providerDTO.getHospitalName());
		provider.setLicenseNumber(providerDTO.getLicenseNumber());
		provider.setSpecialization(providerDTO.getSpecialization());
		provider.setAddress(providerDTO.getAddress());

		Provider savedProvider = providerRepository.save(provider);

		logger.info("Provider added successfully with id {}", savedProvider.getProviderId());

		ProviderDTO responseDTO = new ProviderDTO();

		responseDTO.setProviderId(savedProvider.getProviderId());
		responseDTO.setUserId(savedProvider.getUser().getUserId());
		responseDTO.setHospitalName(savedProvider.getHospitalName());
		responseDTO.setLicenseNumber(savedProvider.getLicenseNumber());
		responseDTO.setSpecialization(savedProvider.getSpecialization());
		responseDTO.setAddress(savedProvider.getAddress());

		return responseDTO;
	}

	@Override
	public ProviderDTO getProviderById(Integer providerId) {

		logger.info("Fetching provider with id {}", providerId);

		Provider provider = providerRepository.findById(providerId).orElseThrow(() -> {

			logger.warn("Provider not found with id {}", providerId);

			return new ProviderNotFoundException("Provider not found with ID: " + providerId);
		});

		ProviderDTO providerDTO = new ProviderDTO();

		providerDTO.setProviderId(provider.getProviderId());
		providerDTO.setUserId(provider.getUser().getUserId());
		providerDTO.setHospitalName(provider.getHospitalName());
		providerDTO.setLicenseNumber(provider.getLicenseNumber());
		providerDTO.setSpecialization(provider.getSpecialization());
		providerDTO.setAddress(provider.getAddress());

		return providerDTO;
	}

	@Override
	public List<ProviderDTO> getAllProviders() {

		logger.info("Fetching all providers");

		List<Provider> providers = providerRepository.findAll();

		List<ProviderDTO> providerDTOList = new ArrayList<>();

		for (Provider provider : providers) {

			ProviderDTO providerDTO = new ProviderDTO();

			providerDTO.setProviderId(provider.getProviderId());
			providerDTO.setUserId(provider.getUser().getUserId());
			providerDTO.setHospitalName(provider.getHospitalName());
			providerDTO.setLicenseNumber(provider.getLicenseNumber());
			providerDTO.setSpecialization(provider.getSpecialization());
			providerDTO.setAddress(provider.getAddress());

			providerDTOList.add(providerDTO);
		}

		logger.info("Total providers fetched: {}", providerDTOList.size());

		return providerDTOList;
	}

	@Override
	public ProviderDTO updateProvider(Integer providerId, ProviderDTO providerDTO) {

		logger.info("Updating provider with id {}", providerId);

		Provider provider = providerRepository.findById(providerId).orElseThrow(() -> {

			logger.warn("Provider not found with id {}", providerId);

			return new ProviderNotFoundException("Provider not found with ID: " + providerId);
		});

		User user = userRepository.findById(providerDTO.getUserId()).orElseThrow(() -> {

			logger.warn("User not found with id {}", providerDTO.getUserId());

			return new UserNotFoundException("User not found with ID: " + providerDTO.getUserId());
		});

		provider.setUser(user);
		provider.setHospitalName(providerDTO.getHospitalName());
		provider.setLicenseNumber(providerDTO.getLicenseNumber());
		provider.setSpecialization(providerDTO.getSpecialization());
		provider.setAddress(providerDTO.getAddress());

		Provider updatedProvider = providerRepository.save(provider);

		logger.info("Provider updated successfully with id {}", updatedProvider.getProviderId());

		ProviderDTO responseDTO = new ProviderDTO();

		responseDTO.setProviderId(updatedProvider.getProviderId());
		responseDTO.setUserId(updatedProvider.getUser().getUserId());
		responseDTO.setHospitalName(updatedProvider.getHospitalName());
		responseDTO.setLicenseNumber(updatedProvider.getLicenseNumber());
		responseDTO.setSpecialization(updatedProvider.getSpecialization());
		responseDTO.setAddress(updatedProvider.getAddress());

		return responseDTO;
	}

	@Override
	public void deleteProvider(Integer providerId) {

		logger.info("Deleting provider with id {}", providerId);

		Provider provider = providerRepository.findById(providerId).orElseThrow(() -> {

			logger.warn("Provider not found with id {}", providerId);

			return new ProviderNotFoundException("Provider not found with ID: " + providerId);
		});

		providerRepository.delete(provider);

		logger.info("Provider deleted successfully with id {}", providerId);
	}
	
	@Override
	public ProviderDTO getProviderByEmail(String email) {

	    Provider provider = providerRepository.findByUserEmail(email);

	    if (provider == null) {
	        throw new ProviderNotFoundException("Provider not found");
	    }

	    ProviderDTO dto = new ProviderDTO();

	    dto.setProviderId(provider.getProviderId());
	    dto.setUserId(provider.getUser().getUserId());
	    dto.setHospitalName(provider.getHospitalName());
	    dto.setLicenseNumber(provider.getLicenseNumber());
	    dto.setSpecialization(provider.getSpecialization());
	    dto.setAddress(provider.getAddress());

	    return dto;
	}
}