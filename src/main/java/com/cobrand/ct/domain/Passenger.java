package com.cobrand.ct.domain;


import org.apache.commons.lang3.StringUtils;

public class Passenger {

    private String firstName = "REGULAR";
    private String lastName = "MAIN";
    private String  dateOfBirth;

	private Passenger() {
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }
    /**
   	 * @return the dateOfBirth
   	 */
   	public String getDateOfBirth() {
   		return dateOfBirth;
   	}
    public static class PassengerBuilder {

        private Passenger passenger = new Passenger();

        public PassengerBuilder withFirstName(String firstName) {
            if (StringUtils.isNotEmpty(firstName)) {
                this.passenger.firstName = firstName;
            }
            return this;
        }

        public PassengerBuilder withLastName(String lastName) {
            if (StringUtils.isNotEmpty(lastName)) {
                this.passenger.lastName = lastName;
            }
            return this;
        }
        public PassengerBuilder withDateOfBirth(String dateOfBirth) {
            if (dateOfBirth!=null) {
                this.passenger.dateOfBirth = dateOfBirth;
            }
            return this;
        }
        public Passenger build() {
            return passenger;
        }
    }
}
