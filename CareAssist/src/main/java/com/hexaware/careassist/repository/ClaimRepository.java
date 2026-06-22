package com.hexaware.careassist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.careassist.entity.Claim;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Integer> {

	List<Claim> findByStatus(String status);

}