package com.pp.plangenerator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pp.plangenerator.model.PlanGeneratorRequest;
import com.pp.plangenerator.service.PlanGeneratorService;

/**
 * 
 * @author PRATHAMESH PAWAR
 *
 */
@RestController
public class PlanGeneratorController {

	@Autowired
	private PlanGeneratorService planGeneratorService;

	@PostMapping(path = "/generate-plan", consumes = "application/json", produces = "application/json")
	public String generatePlan(@RequestBody PlanGeneratorRequest planGeneratorRequest) {
		return planGeneratorService.generatePlan(planGeneratorRequest);
	}

	@GetMapping(path = "/ping", produces = "application/json")
	public String ping() {
		return "Hi from Plan generator application";
	}
}
