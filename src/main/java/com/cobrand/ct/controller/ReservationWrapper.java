package com.cobrand.ct.controller;

import java.util.List;

public class ReservationWrapper {
	private Flight flight;
	private List<Passenger> passengers;
    private String testId;


	/**
	 * @return the testId
	 */
	public String getTestId() {
		return testId;
	}

	/**
	 * @param testId the testId to set
	 */
	public void setTestId(String testId) {
		this.testId = testId;
	}

	/**
	 * @return the flight
	 */
	public Flight getFlight() {
		return flight;
	}

	/**
	 * @return the passengers
	 */
	public List<Passenger> getPassengers() {
		return passengers;
	}

	/**
	 * @param flight
	 *            the flight to set
	 */
	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	/**
	 * @param passengers
	 *            the passengers to set
	 */
	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

}

class Flight {
	private String origin;
	private String destination;
	private Integer departureTimeRange;
	private Integer departTime;
	private Integer departingNoOfDaysFromToday=1;
	private Integer returnPlusNumberOfDays=1;
	private Integer returnTime=0;
	private String carrier;

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @return the departureTimeRange
	 */
	public Integer getDepartureTimeRange() {
		return departureTimeRange;
	}

	/**
	 * @return the departTime
	 */
	public Integer getDepartTime() {
		return departTime;
	}

	/**
	 * @return the departingNoOfDaysFromToday
	 */
	public Integer getDepartingNoOfDaysFromToday() {
		return departingNoOfDaysFromToday;
	}

	/**
	 * @return the returnPlusNumberOfDays
	 */
	public Integer getReturnPlusNumberOfDays() {
		return returnPlusNumberOfDays;
	}

	/**
	 * @return the returnTime
	 */
	public Integer getReturnTime() {
		return returnTime;
	}

	/**
	 * @param origin
	 *            the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @param destination
	 *            the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @param departureTimeRange
	 *            the departureTimeRange to set
	 */
	public void setDepartureTimeRange(Integer departureTimeRange) {
		this.departureTimeRange = departureTimeRange;
	}

	/**
	 * @param departTime
	 *            the departTime to set
	 */
	public void setDepartTime(Integer departTime) {
		this.departTime = departTime;
	}

	/**
	 * @param departingNoOfDaysFromToday
	 *            the departingNoOfDaysFromToday to set
	 */
	public void setDepartingNoOfDaysFromToday(Integer departingNoOfDaysFromToday) {
		this.departingNoOfDaysFromToday = departingNoOfDaysFromToday;
	}

	/**
	 * @param returnPlusNumberOfDays
	 *            the returnPlusNumberOfDays to set
	 */
	public void setReturnPlusNumberOfDays(Integer returnPlusNumberOfDays) {
		this.returnPlusNumberOfDays = returnPlusNumberOfDays;
	}

	/**
	 * @param returnTime
	 *            the returnTime to set
	 */
	public void setReturnTime(Integer returnTime) {
		this.returnTime = returnTime;
	}

	public void setCarrier(String carrier) {
		this.carrier= carrier;
		// TODO Auto-generated method stub
		
	}

}

class Passenger {
	private String firstName;
	private String lastName;
	private Integer advantageNumber;
	private String oneWorldCarrier="AA";
	private String carrier="AA";
	private String dateOfBirth;
	private String countryOfResidence;
	private String passport;
	private String countryOfPassportIssued;
	private String passportExpiration;
	private String phoneNumber;

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the advantageNumber
	 */
	public Integer getAdvantageNumber() {
		return advantageNumber;
	}

	/**
	 * @return the oneWorldCarrier
	 */
	public String getOneWorldCarrier() {
		return oneWorldCarrier;
	}

	/**
	 * @return the carrier
	 */
	public String getCarrier() {
		return carrier;
	}

	/**
	 * @return the dateOfBirth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @return the countryOfResidence
	 */
	public String getCountryOfResidence() {
		return countryOfResidence;
	}

	/**
	 * @return the passport
	 */
	public String getPassport() {
		return passport;
	}

	/**
	 * @return the countryOfPassportIssued
	 */
	public String getCountryOfPassportIssued() {
		return countryOfPassportIssued;
	}

	/**
	 * @return the passportExpiration
	 */
	public String getPassportExpiration() {
		return passportExpiration;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @param advantageNumber
	 *            the advantageNumber to set
	 */
	public void setAdvantageNumber(Integer advantageNumber) {
		this.advantageNumber = advantageNumber;
	}

	/**
	 * @param oneWorldCarrier
	 *            the oneWorldCarrier to set
	 */
	public void setOneWorldCarrier(String oneWorldCarrier) {
		this.oneWorldCarrier = oneWorldCarrier;
	}

	/**
	 * @param carrier
	 *            the carrier to set
	 */
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	/**
	 * @param dateOfBirth
	 *            the dateOfBirth to set
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @param countryOfResidence
	 *            the countryOfResidence to set
	 */
	public void setCountryOfResidence(String countryOfResidence) {
		this.countryOfResidence = countryOfResidence;
	}

	/**
	 * @param passport
	 *            the passport to set
	 */
	public void setPassport(String passport) {
		this.passport = passport;
	}

	/**
	 * @param countryOfPassportIssued
	 *            the countryOfPassportIssued to set
	 */
	public void setCountryOfPassportIssued(String countryOfPassportIssued) {
		this.countryOfPassportIssued = countryOfPassportIssued;
	}

	/**
	 * @param passportExpiration
	 *            the passportExpiration to set
	 */
	public void setPassportExpiration(String passportExpiration) {
		this.passportExpiration = passportExpiration;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}