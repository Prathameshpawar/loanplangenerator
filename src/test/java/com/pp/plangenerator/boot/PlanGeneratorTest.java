package com.pp.plangenerator.boot;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pp.plangenerator.model.PlanGeneratorRequest;
import com.pp.plangenerator.model.PlanGeneratorResult;
import com.pp.plangenerator.service.PlanGeneratorService;
import com.pp.plangenerator.utils.PlanGeneratorUtils;

/**
 * 
 * @author PRATHAMESH PAWAR
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PlanGeneratorTest {

	@Autowired
	private PlanGeneratorUtils planGeneratorUtils;

	@Autowired
	private PlanGeneratorService planGeneratorService;

	private PlanGeneratorRequest planGeneratorRequest;

	@Before
	public void initUseCase() {
		planGeneratorRequest = new PlanGeneratorRequest();
		planGeneratorRequest.setDuration(24);
		planGeneratorRequest.setLoanAmount(5000);
		planGeneratorRequest.setNominalRate(5);
		planGeneratorRequest.setStartDate("2018-01-01T00:00:01Z");
	}

	@Test
	public void testDate() {
		String dateConverter = planGeneratorUtils.convertDate("yyyy-MM-dd'T'HH:mm:ss'Z'", "dd-MM-yyyy",
				planGeneratorRequest.getStartDate());
		Assert.assertEquals("01-01-2018", dateConverter);

		String nextDate = planGeneratorUtils.getNextDate("dd-MM-yyyy", dateConverter);
		Assert.assertEquals("01-02-2018", nextDate);
	}

	@Test
	public void testPlanGenerator() {
		String generatePlan = planGeneratorService.generatePlan(planGeneratorRequest);
		List<PlanGeneratorResult> lsPlanGeneratorResult = planGeneratorUtils.jsonToObjects(generatePlan);
		PlanGeneratorResult firstPayment = lsPlanGeneratorResult.get(0);

		Assert.assertNotNull(lsPlanGeneratorResult);
		Assert.assertEquals("5000.00", firstPayment.getInitialOutstandingPrincipal());
		Assert.assertEquals("20.83", firstPayment.getInterest());
		Assert.assertEquals("198.53", firstPayment.getPrincipal());
		Assert.assertEquals("4801.47", firstPayment.getRemainingOutstandingPrincipal());
		Assert.assertEquals("219.36", firstPayment.getBorrowerPaymentAmount());
	}
}
