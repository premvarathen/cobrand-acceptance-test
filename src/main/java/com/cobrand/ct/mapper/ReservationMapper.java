package com.cobrand.ct.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cobrand.citi.constant.CobrandConstants;
import com.cobrand.ct.domain.Reservation;


public class ReservationMapper {

    private final FlightMapper flightMapper = new FlightMapper();
    private final PassengerMapper passengerMapper = new PassengerMapper();

    public Reservation map(List<Map<String, String>> reservationRawData) {

        return new Reservation.ReservationBuilder()
                .withTestId((reservationRawData.get(0).get(CobrandConstants.FEATURE_KEY_TEST_ID) != null) ?
                        reservationRawData.get(0).get(CobrandConstants.FEATURE_KEY_TEST_ID) :
                        "")
                .withPassengers(passengerMapper.map(reservationRawData))
                .withFlight(flightMapper.map(reservationRawData))
                .withCorporate(Boolean.TRUE.toString()
                        .equalsIgnoreCase(reservationRawData.get(0).get(CobrandConstants.FEATURE_KEY_CORPORATE)))
                .withExitSeatRow(Boolean.TRUE.toString()
                        .equalsIgnoreCase(reservationRawData.get(0).get(CobrandConstants.FEATURE_KEY_EXIT_ROW_SEAT)))
                .withPurchased(Boolean.TRUE.toString()
                        .equalsIgnoreCase(reservationRawData.get(0).get(CobrandConstants.FEATURE_KEY_PURCHASED)))
                .withAdvantange(
                		transformKeysToList(reservationRawData,CobrandConstants.FEATURE_KEY_AADV))
                .withOneWorldCarrier(reservationRawData.get(0).get(CobrandConstants.ONE_WORLD_CARRIER))
                .withLocale(reservationRawData.get(0).get(CobrandConstants.FEATURE_KEY_LOCALE))
                .withCountryOfResidence(transformKeysToList(reservationRawData,CobrandConstants.FEATURE_KEY_COUNTRY_OF_RESIDENCE))
                .build();
    }
    
    private List<String> transformKeysToList(List<Map<String, String>> reservationRawData, String key) {
        List<String> result = new ArrayList<>();
    	reservationRawData.forEach(map-> result.add(map.get(key)));
        return result;
    }
}
