package com.viewnext.suma.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viewnext.suma.domain.Suma;
import com.viewnext.suma.service.SumaService;


/**
 * This class implements a REST API for our Suma application.
 */
@RestController
@RequestMapping("/sumas")
final class SumaController {

	private final SumaService sumaService;
	
	@Autowired
	public SumaController(SumaService sumaService) {
		this.sumaService = sumaService;
	}
	
	@GetMapping("/random")
	public Suma getRandomSuma() {
		return sumaService.createRandomPlusOperation();
	}
}
