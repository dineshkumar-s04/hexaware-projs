package com.hexaware.cricketmanagement.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.cricketmanagement.dto.PlayerDTO;
import com.hexaware.cricketmanagement.entity.Player;
import com.hexaware.cricketmanagement.exception.PlayerNotFoundException;
import com.hexaware.cricketmanagement.repository.PlayerRepository;
import com.hexaware.cricketmanagement.service.IPlayerService;

@Service
public class PlayerServiceImpl implements IPlayerService {

	@Autowired
	private PlayerRepository playerRepository;

	@Override
	public PlayerDTO addPlayer(PlayerDTO playerDTO) {

		Player player = new Player();

		player.setPlayerId(playerDTO.getPlayerId());
		player.setPlayerName(playerDTO.getPlayerName());
		player.setJerseyNumber(playerDTO.getJerseyNumber());
		player.setRole(playerDTO.getRole());
		player.setTotalMatches(playerDTO.getTotalMatches());
		player.setTeamName(playerDTO.getTeamName());
		player.setCountryStateName(playerDTO.getCountryStateName());
		player.setDescription(playerDTO.getDescription());

		Player savedPlayer = playerRepository.save(player);

		PlayerDTO responseDTO = new PlayerDTO();

		responseDTO.setPlayerId(savedPlayer.getPlayerId());
		responseDTO.setPlayerName(savedPlayer.getPlayerName());
		responseDTO.setJerseyNumber(savedPlayer.getJerseyNumber());
		responseDTO.setRole(savedPlayer.getRole());
		responseDTO.setTotalMatches(savedPlayer.getTotalMatches());
		responseDTO.setTeamName(savedPlayer.getTeamName());
		responseDTO.setCountryStateName(savedPlayer.getCountryStateName());
		responseDTO.setDescription(savedPlayer.getDescription());

		return responseDTO;
	}

	@Override
	public PlayerDTO getPlayerById(Integer playerId) {

		Player player = playerRepository.findById(playerId)
				.orElseThrow(() -> new PlayerNotFoundException("Player not found with ID: " + playerId));

		PlayerDTO playerDTO = new PlayerDTO();

		playerDTO.setPlayerId(player.getPlayerId());
		playerDTO.setPlayerName(player.getPlayerName());
		playerDTO.setJerseyNumber(player.getJerseyNumber());
		playerDTO.setRole(player.getRole());
		playerDTO.setTotalMatches(player.getTotalMatches());
		playerDTO.setTeamName(player.getTeamName());
		playerDTO.setCountryStateName(player.getCountryStateName());
		playerDTO.setDescription(player.getDescription());

		return playerDTO;
	}

	@Override
	public List<PlayerDTO> getAllPlayers() {

		List<Player> players = playerRepository.findAll();

		List<PlayerDTO> playerDTOList = new ArrayList<>();

		for (Player player : players) {

			PlayerDTO playerDTO = new PlayerDTO();

			playerDTO.setPlayerId(player.getPlayerId());
			playerDTO.setPlayerName(player.getPlayerName());
			playerDTO.setJerseyNumber(player.getJerseyNumber());
			playerDTO.setRole(player.getRole());
			playerDTO.setTotalMatches(player.getTotalMatches());
			playerDTO.setTeamName(player.getTeamName());
			playerDTO.setCountryStateName(player.getCountryStateName());
			playerDTO.setDescription(player.getDescription());

			playerDTOList.add(playerDTO);
		}

		return playerDTOList;
	}

	@Override
	public PlayerDTO updatePlayer(Integer playerId, PlayerDTO playerDTO) {

		Player player = playerRepository.findById(playerId)
				.orElseThrow(() -> new PlayerNotFoundException("Player not found with ID: " + playerId));

		player.setPlayerName(playerDTO.getPlayerName());
		player.setJerseyNumber(playerDTO.getJerseyNumber());
		player.setRole(playerDTO.getRole());
		player.setTotalMatches(playerDTO.getTotalMatches());
		player.setTeamName(playerDTO.getTeamName());
		player.setCountryStateName(playerDTO.getCountryStateName());
		player.setDescription(playerDTO.getDescription());

		Player updatedPlayer = playerRepository.save(player);

		PlayerDTO responseDTO = new PlayerDTO();

		responseDTO.setPlayerId(updatedPlayer.getPlayerId());
		responseDTO.setPlayerName(updatedPlayer.getPlayerName());
		responseDTO.setJerseyNumber(updatedPlayer.getJerseyNumber());
		responseDTO.setRole(updatedPlayer.getRole());
		responseDTO.setTotalMatches(updatedPlayer.getTotalMatches());
		responseDTO.setTeamName(updatedPlayer.getTeamName());
		responseDTO.setCountryStateName(updatedPlayer.getCountryStateName());
		responseDTO.setDescription(updatedPlayer.getDescription());

		return responseDTO;
	}

	@Override
	public void deletePlayer(Integer playerId) {

		Player player = playerRepository.findById(playerId)
				.orElseThrow(() -> new PlayerNotFoundException("Player not found with ID: " + playerId));

		playerRepository.delete(player);
	}
}