package com.hexaware.cricketmanagement.service;

import java.util.List;

import com.hexaware.cricketmanagement.dto.PlayerDTO;

public interface IPlayerService {
	
	PlayerDTO addPlayer(PlayerDTO playerDTO);

	PlayerDTO getPlayerById(Integer playerId);

	List<PlayerDTO> getAllPlayers();

	PlayerDTO updatePlayer(Integer playerId, PlayerDTO playerDTO);

	void deletePlayer(Integer playerId);
	
}