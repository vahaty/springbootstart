package com.viewnext.controlpartida.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viewnext.controlpartida.domain.GameStats;
import com.viewnext.controlpartida.service.GameService;
/**
 * This class implements a REST API for the GameControl User Statistics service.
 */
@RestController
@RequestMapping("/estadisticas")
class UserStatsController{
	private final GameService gameService;

	@Autowired
	public UserStatsController(final GameService gameService) {
		this.gameService = gameService;
	}
	
	@GetMapping("/{resultId}")
	public ResponseEntity<GameStats> getRSumaByID(final @PathVariable ("resultId") Long resultId) {
		return ResponseEntity.ok(gameService.retrieveStatsForUser(resultId));

	}
}
