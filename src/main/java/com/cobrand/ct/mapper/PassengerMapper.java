package com.cobrand.ct.mapper;

import static aa.ct.fly.bdd.pnr.config.BDDConstants.FEATURE_KEY_FIRST_NAME;
import static aa.ct.fly.bdd.pnr.config.BDDConstants.FEATURE_KEY_LAST_NAME;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cobrand.ct.domain.Passenger;

import aa.ct.fly.bdd.pnr.namegenerator.RandomNameGenerator;

public class PassengerMapper {

    private RandomNameGenerator nameGenerator = new RandomNameGenerator();

    List<Passenger> map(List<Map<String, String>> reservationRawData) {
        //Map<String, String> rawData = new HashMap<String, String>();
        List<Passenger> passengers = new ArrayList<>();
        reservationRawData.forEach(map-> passengers.add(createPassenger(map.get(FEATURE_KEY_FIRST_NAME), map)));
        return passengers;
       /* if (reservationRawData.size() == 1) {
            rawData = reservationRawData.get(0);
        }
        
        String  paxCount = rawData.get(FEATURE_KEY_TOTAL_NUMBER_OF_PASSENGERS);
        
        if(StringUtils.isNotEmpty(paxCount)) {
            return IntStream.range(0, Integer.parseInt(paxCount))
                    .boxed()
                    .map(i -> createPassenger(nameGenerator.getRandomMaleName(), nameGenerator.getRandomLastName()))
                    .collect(toList());
        }
        else {
            String[] firstNameRawData = rawData.get(FEATURE_KEY_FIRST_NAME).split(";");
            Map<String, String> finalRawData = rawData;
            return Arrays.stream(firstNameRawData)
                    .map(firstName -> createPassenger(firstName, finalRawData))
                    .collect(toList());
        }*/
    }

    private Passenger createPassenger(String firstName, Map<String, String> rawData1) {
        return new Passenger.PassengerBuilder()
                .withFirstName(firstName)
                .withLastName(rawData1.get(FEATURE_KEY_LAST_NAME))
                .withDateOfBirth(rawData1.get("dateOfBirth"))
                .build();
    }

    private Passenger createPassenger(String firstName, String lastName) {
        return new Passenger.PassengerBuilder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .build();
    }
}
