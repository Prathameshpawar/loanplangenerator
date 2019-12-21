package com.pp.plangenerator.service;

import com.pp.plangenerator.model.PlanGeneratorRequest;

/**
 * Contains method for generating loan plan.
 * 
 * @author PRATHAMESH PAWAR
 *
 */
public interface PlanGeneratorService {

	/**
	 * generates load re-payment plan for given input.
	 * 
	 * @param planGeneratorRequest
	 *            input json data
	 * @return generated plan
	 */
	public String generatePlan(PlanGeneratorRequest planGeneratorRequest);
}
