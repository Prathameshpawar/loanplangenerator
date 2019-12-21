package com.pp.plangenerator.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.pp.plangenerator.model.PlanGeneratorResult;

/**
 * 
 * @author PRATHAMESH PAWAR
 *
 */
@Component
public class PlanGeneratorUtils {

	public String convertObjectsToJSON(List<PlanGeneratorResult> lsPlanGenerator) {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonOutput = "";
		// Set pretty printing of json
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			jsonOutput = objectMapper.writeValueAsString(lsPlanGenerator);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonOutput;
	}

	public String convertDate(String inputDateFormat, String outputDateFormat, String inputDate) {

		SimpleDateFormat inputFormat = new SimpleDateFormat(inputDateFormat);
		SimpleDateFormat outputFormat = new SimpleDateFormat(outputDateFormat);
		Date date = null;
		try {
			date = inputFormat.parse(inputDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String formattedDate = outputFormat.format(date);
		return formattedDate;
	}

	public String getNextDate(String inputDateFormat, String inputDate) {
		Date date = null;
		try {
			date = new SimpleDateFormat(inputDateFormat).parse(inputDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);

		Date date1 = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat(inputDateFormat);
		String nextDate = dateFormat.format(date1);
		return nextDate;
	}

	public String getPrevDate(String inputDateFormat, String inputDate) {
		Date date = null;
		try {
			date = new SimpleDateFormat(inputDateFormat).parse(inputDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);

		Date date1 = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat(inputDateFormat);
		String nextDate = dateFormat.format(date1);
		return nextDate;
	}

	public List<PlanGeneratorResult> jsonToObjects(String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<PlanGeneratorResult> lsPlanGeneratorResult = null;
		try {
			lsPlanGeneratorResult = Arrays.asList(objectMapper.readValue(json, PlanGeneratorResult[].class));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lsPlanGeneratorResult;
	}

}
