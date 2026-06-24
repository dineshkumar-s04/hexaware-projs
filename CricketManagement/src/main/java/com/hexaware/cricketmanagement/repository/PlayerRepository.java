package com.hexaware.cricketmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.cricketmanagement.entity.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

	
}
