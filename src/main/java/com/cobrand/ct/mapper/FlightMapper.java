package com.cobrand.ct.mapper;

import static aa.ct.fly.bdd.pnr.config.BDDConstants.FEATURE_KEY_CARRIER;
import static aa.ct.fly.bdd.pnr.config.BDDConstants.FEATURE_KEY_CONNECT_CITY;
import static aa.ct.fly.bdd.pnr.config.BDDConstants.FEATURE_KEY_DEPARTING_NO_OF_DAYS_FROM_TODAY;
import static aa.ct.fly.bdd.pnr.config.BDDConstants.FEATURE_KEY_DEPART_TIME;
import static aa.ct.fly.bdd.pnr.config.BDDConstants.FEATURE_KEY_DESTINATION;
import static aa.ct.fly.bdd.pnr.config.BDDConstants.FEATURE_KEY_ORIGIN;
import static aa.ct.fly.bdd.pnr.config.BDDConstants.FEATURE_KEY_RETURN_PLUS_NUMBER_OF_DAYS;
import static aa.ct.fly.bdd.pnr.config.BDDConstants.FEATURE_KEY_RETURN_TIME;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cobrand.ct.domain.Flight;

import aa.ct.fly.bdd.pnr.compromisedException.InsufficientTestDataSetUp;

public class FlightMapper {

  Flight map(List<Map<String, String>> reservationRawData) {
    //Assume Currently only one flight info. Update later.
    Map<String, String> rawData = new HashMap<String, String>();

	  if (reservationRawData.size() >0 ) {
		  rawData = new HashMap<String, String>(reservationRawData.get(0));
		  evaluateRow(rawData, "DepartingNoOfDaysFromToday", "DepartTime");
		  evaluateRow(rawData, "ReturnPlusNumberOfDays", "ReturnTime");
	  }
    
    return new Flight.FlightBuilder()
        .withOrigin(rawData.get(FEATURE_KEY_ORIGIN))
        .withDestination(rawData.get(FEATURE_KEY_DESTINATION))
        .withDepartingNoOfDaysFromToday(rawData.get(FEATURE_KEY_DEPARTING_NO_OF_DAYS_FROM_TODAY))
        .withDepartTime(rawData.get(FEATURE_KEY_DEPART_TIME))
        .withCarrier(rawData.get(FEATURE_KEY_CARRIER))
        .withReturnPlusNumberOfDays(rawData.get(FEATURE_KEY_RETURN_PLUS_NUMBER_OF_DAYS))
        .withReturnTime(rawData.get(FEATURE_KEY_RETURN_TIME))
        .withConnectCity(rawData.get(FEATURE_KEY_CONNECT_CITY))
        .build();
  }


	private void evaluateRow(Map<String, String> row, String dayKey, String timeKey) {

		String day = row.get(dayKey);
		String time = row.get(timeKey);

		if ((day == null) || (time == null)) {
			return;
		}

		int departurePlusDays = Integer.parseInt(day);

		time = time.toLowerCase();
		if (!(time.endsWith("p") || time.endsWith("a"))) {
			int addHours = Integer.parseInt(time.substring(3));
			if (addHours > 0 && addHours < 24) {
				LocalDateTime timeNow = LocalDateTime.now();
				LocalDateTime timeAfter = timeNow.plusHours(addHours);
				int hoursAfter = timeAfter.getHour();
				time = getHoursStr(hoursAfter) + ((hoursAfter >= 12) ? "p" : "a");
				if (hoursAfter < addHours) {
					departurePlusDays++;
					day = String.valueOf(departurePlusDays);
				}
			} else {
				throw new InsufficientTestDataSetUp("Inavlid " + timeKey);
			}
		}

		row.put(dayKey, day);
		row.put(timeKey, time);


	}

	private int getHoursStr(int hoursAfter) {
		hoursAfter = hoursAfter > 12 ? (hoursAfter - 12) : hoursAfter;
		hoursAfter = (hoursAfter == 0) ? 12 : hoursAfter;
		return hoursAfter;
	}
	
}
