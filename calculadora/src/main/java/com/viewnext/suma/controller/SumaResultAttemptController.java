package com.viewnext.suma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viewnext.suma.domain.Suma;
import com.viewnext.suma.domain.SumaResultAttempt;
import com.viewnext.suma.service.SumaService;

/**
 * This class provides a REST API to POST the attempts from users.
 */

@RestController
@RequestMapping("/resultados-suma")
final class SumaResultAttemptController {
	private final SumaService sumaService;

	@Autowired
	public SumaResultAttemptController(final SumaService sumaService) {
		this.sumaService = sumaService;
	}

	@PostMapping
	ResponseEntity<SumaResultAttempt> sumapostResultardo(@RequestBody SumaResultAttempt sumaresultattempt) {
		return ResponseEntity.ok(sumaService.checkAttempt(sumaresultattempt));

	}
	@GetMapping
	public ResponseEntity<List<SumaResultAttempt>> getRandomSuma(final @RequestParam ("alias") String alias) {
		return ResponseEntity.ok(sumaService.getStatsForUser(alias));

	}
	@GetMapping("/{resultId}")
	public ResponseEntity<SumaResultAttempt> getRSumaByID(final @PathVariable ("resultId") Long resultId) {
		return ResponseEntity.ok(sumaService.getResultById(resultId));

	}
}
