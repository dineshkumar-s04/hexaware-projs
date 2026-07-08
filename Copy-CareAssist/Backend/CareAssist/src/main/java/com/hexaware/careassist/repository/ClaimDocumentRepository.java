package com.hexaware.careassist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.careassist.entity.ClaimDocument;

@Repository
public interface ClaimDocumentRepository extends JpaRepository<ClaimDocument, Integer> {

	List<ClaimDocument> findByClaimClaimId(Integer claimId);
	
}