package com.viewnext.controlpartida.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viewnext.controlpartida.domain.LeaderBoardRow;
import com.viewnext.controlpartida.service.LeaderBoardService;

/**
 * This class implements a REST API for the GameControl LeaderBoard service.
 */
@RestController
@RequestMapping("/lideres")
class LeaderBoardController{
	private final LeaderBoardService leaderBoardService;

	@Autowired
	public LeaderBoardController(final LeaderBoardService leaderBoardService) {
		this.leaderBoardService = leaderBoardService;
	}
	
	@GetMapping
	public ResponseEntity<List<LeaderBoardRow>> getRandomSuma() {
		return ResponseEntity.ok(leaderBoardService.getCurrentLeaderBoard());

	}
}
