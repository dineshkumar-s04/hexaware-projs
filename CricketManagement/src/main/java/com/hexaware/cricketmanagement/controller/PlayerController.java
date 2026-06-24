package com.hexaware.cricketmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.cricketmanagement.dto.PlayerDTO;
import com.hexaware.cricketmanagement.service.IPlayerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/players")
@Validated
public class PlayerController {

	@Autowired
	private IPlayerService playerService;

	@PostMapping("/add")
	public PlayerDTO addPlayer(@Valid @RequestBody PlayerDTO playerDTO) {

		return playerService.addPlayer(playerDTO);
	}

	@GetMapping("/get/{playerId}")
	public PlayerDTO getPlayerById(@PathVariable Integer playerId) {

		return playerService.getPlayerById(playerId);
	}

	@GetMapping("/getall")
	public List<PlayerDTO> getAllPlayers() {

		return playerService.getAllPlayers();
	}

	@PutMapping("/update/{playerId}")
	public PlayerDTO updatePlayer(@PathVariable Integer playerId, @Valid @RequestBody PlayerDTO playerDTO) {

		return playerService.updatePlayer(playerId, playerDTO);
	}

	@DeleteMapping("/delete/{playerId}")
	public void deletePlayer(@PathVariable Integer playerId) {

		playerService.deletePlayer(playerId);
	}
}