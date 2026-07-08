package com.hexaware.careassist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.careassist.entity.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {
	Provider findByUserEmail(String email);

}