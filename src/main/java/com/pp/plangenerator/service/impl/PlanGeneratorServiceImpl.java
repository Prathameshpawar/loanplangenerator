package com.pp.plangenerator.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pp.plangenerator.functions.AnnuityFunction;
import com.pp.plangenerator.functions.PlanGeneratorResultFunction;
import com.pp.plangenerator.functions.PrincipleAmountFunction;
import com.pp.plangenerator.model.PlanGeneratorRequest;
import com.pp.plangenerator.model.PlanGeneratorResult;
import com.pp.plangenerator.service.PlanGeneratorService;
import com.pp.plangenerator.utils.PlanGeneratorUtils;

/**
 * Implementation of {@link PlanGeneratorService}
 * 
 * @author PRATHAMESH PAWAR
 *
 */
@Service
public class PlanGeneratorServiceImpl implements PlanGeneratorService {

	@Autowired
	private PlanGeneratorUtils planGeneratorUtils;

	private static final int DAYS_OF_MONTH = 30;
	private static final int DAYS_IN_YEAR = 360;
	private static final String YYYY_MM_DD = "yyyy-MM-dd";
	private static final String TIME_ZONE = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	private static double remOutStandPrinciple;
	private static DecimalFormat df2 = new DecimalFormat("#.##");
	private static DecimalFormat df3 = new DecimalFormat("#.00");
	private String date = null;

	// defining functional interfaces

	// functional interface for creating new plan result.
	PlanGeneratorResultFunction<Double, PlanGeneratorResult> planGeneratorResultFunction = (outstandingAmount, interest,
			annuity, principle) -> {
		PlanGeneratorResult planGeneratorResult = new PlanGeneratorResult();
		planGeneratorResult.setBorrowerPaymentAmount(df2.format(annuity));
		date = planGeneratorUtils.getNextDate(YYYY_MM_DD, date);
		planGeneratorResult.setDate(planGeneratorUtils.convertDate("yy-MM-dd", TIME_ZONE, date));
		planGeneratorResult.setInitialOutstandingPrincipal(df3.format(outstandingAmount));
		planGeneratorResult.setInterest(df2.format(interest));
		planGeneratorResult.setPrincipal(df2.format(principle));
		planGeneratorResult.setRemainingOutstandingPrincipal(df2.format(remOutStandPrinciple));
		return planGeneratorResult;
	};

	// functional interface for calculating annuity
	AnnuityFunction<Number, Double> annuityFunction = (loanAmount, intPerMonth, duration) -> {
		BigDecimal tempAnnuity = new BigDecimal(loanAmount.doubleValue()
				/ ((Math.pow(1 + intPerMonth.doubleValue(), duration.intValue()) - 1) / (intPerMonth.doubleValue()
						* (Math.pow(1 + intPerMonth.doubleValue(), duration.intValue())))));
		tempAnnuity = tempAnnuity.setScale(2, BigDecimal.ROUND_UP);
		return tempAnnuity.doubleValue();
	};

	// functional interface for calculating principle amount
	PrincipleAmountFunction<Double, Double> principleAmountFunction = (annuity, interest, intOutPrincipleAmt) -> {
		return annuity - interest > intOutPrincipleAmt ? intOutPrincipleAmt : annuity - interest;
	};

	@Override
	public String generatePlan(PlanGeneratorRequest planGeneratorRequest) {
		List<PlanGeneratorResult> lsPlanGenerator = calculatePlan(planGeneratorRequest);
		return planGeneratorUtils.convertObjectsToJSON(lsPlanGenerator);
	}

	private List<PlanGeneratorResult> calculatePlan(PlanGeneratorRequest planGeneratorRequest) {
		List<PlanGeneratorResult> lsPlans = new LinkedList<>();

		date = setDate(planGeneratorRequest);

		int duration = planGeneratorRequest.getDuration();
		double intPerMonth = planGeneratorRequest.getNominalRate() / 100 / 12;

		IntStream.rangeClosed(1, planGeneratorRequest.getDuration()).forEach(value -> {
			calculatePlanDetails(planGeneratorRequest, lsPlans, duration, intPerMonth);
		});

		return lsPlans;
	}

	private String setDate(PlanGeneratorRequest planGeneratorRequest) {
		date = planGeneratorUtils.convertDate(TIME_ZONE, YYYY_MM_DD, planGeneratorRequest.getStartDate());
		return planGeneratorUtils.getPrevDate(YYYY_MM_DD, date);
	}

	/**
	 * Calculates plan details. such as interest,annuity,principle amount etc.
	 * 
	 * @param planGeneratorRequest
	 *            input details of plan generator
	 * @param lsPlans
	 *            plan details list
	 * @param duration
	 *            duration of plan
	 * @param intPerMonth
	 *            interest per month of plan
	 */
	private void calculatePlanDetails(PlanGeneratorRequest planGeneratorRequest, List<PlanGeneratorResult> lsPlans,
			int duration, double intPerMonth) {
		double intOutPrincipleAmt = remOutStandPrinciple == 0.0 ?  planGeneratorRequest.getLoanAmount()
				: remOutStandPrinciple;

		double interest = calculateInterest(planGeneratorRequest, intOutPrincipleAmt);

		double annuity = annuityFunction.calculateAnnuity(planGeneratorRequest.getLoanAmount(), intPerMonth, duration);

		double principle = principleAmountFunction.calculatePrincipleAmount(annuity, interest, intOutPrincipleAmt);

		remOutStandPrinciple = Math.abs(intOutPrincipleAmt - principle);

		lsPlans.add(
				planGeneratorResultFunction.setPlanGeneratorResult(intOutPrincipleAmt, interest, annuity, principle));
	}

	private double calculateInterest(PlanGeneratorRequest planGeneratorRequest, double intOutPrincipleAmt) {
		return (planGeneratorRequest.getNominalRate() / 100) * DAYS_OF_MONTH * intOutPrincipleAmt / DAYS_IN_YEAR;
	}

}