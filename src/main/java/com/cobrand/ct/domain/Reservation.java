package com.cobrand.ct.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reservation {
    private boolean corporate;
    private boolean exitRowSeat;
    private List<Passenger> passengers = new ArrayList<>();
    private List<Flight> flights = new ArrayList<>();
    private String testId;
    private boolean purchased;
    private List<String> advantageNumbers = new ArrayList<>();
    private String oneWorldCarrier;
    /**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @return the countryOfResidence
	 */
	

	private String locale;
    private List<String> countryOfResidences = new ArrayList<>();

    public Reservation() {
    }

    public String getTestId() {
        return this.testId;
    }

    public List<Passenger> getPassengers() {
        return Collections.unmodifiableList(passengers);
    }

    public List<Flight> getFlights() {
        return Collections.unmodifiableList(flights);
    }

    public boolean isCorporate() {
        return corporate;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public List<String> getAdvantageNumbers() {
        return advantageNumbers;
    }

    public List<String> getCountryOfResidences() {
		return countryOfResidences;
	}
    public String getOneWorldCarrier() {
        return oneWorldCarrier;
    }

    public boolean isExitRowSeat() {
        return exitRowSeat;
    }

    public static class ReservationBuilder {
        private Reservation reservation = new Reservation();

        public ReservationBuilder withPassengers(List<Passenger> passengers) {
            this.reservation.passengers.addAll(passengers);
            return this;
        }

        public ReservationBuilder withFlight(Flight flight) {
            this.reservation.flights.add(flight);
            return this;
        }

        public ReservationBuilder withCorporate(boolean corporate) {
            this.reservation.corporate = corporate;
            return this;
        }

        public ReservationBuilder withExitSeatRow(boolean exitSeatRow) {
            this.reservation.exitRowSeat = exitSeatRow;
            return this;
        }

        public ReservationBuilder withTestId(String testId) {
            this.reservation.testId = testId;
            return this;
        }

        public ReservationBuilder withPurchased(boolean purchased) {
            this.reservation.purchased = purchased;
            return this;
        }

        public ReservationBuilder withAdvantange(List<String> advantageNumbers) {
            this.reservation.advantageNumbers.addAll(advantageNumbers);
            return this;
        }

        public ReservationBuilder withOneWorldCarrier(String oneWorldCarrier) {
            if (oneWorldCarrier != null) {
                this.reservation.oneWorldCarrier = oneWorldCarrier;
            } else {
                this.reservation.oneWorldCarrier = "";
            }
            return this;
        }
        public ReservationBuilder withCountryOfResidence(List<String>  countryOfResidences) {
        	this.reservation.countryOfResidences.addAll(countryOfResidences);
            return this;
        }
        
        public ReservationBuilder withLocale(String locale) {
            if (locale != null) {
                this.reservation.locale = locale;
            } else {
                this.reservation.locale = "";
            }
            return this;
        }

        public Reservation build() {
            return reservation;
        }
    }
}


