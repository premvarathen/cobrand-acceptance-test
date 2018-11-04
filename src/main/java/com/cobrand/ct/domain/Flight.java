package com.cobrand.ct.domain;

import org.apache.commons.lang3.StringUtils;

public class Flight {
  private String origin;
  private String destination;
  private String departingNoOfDaysFromToday = "1";
  private String departTime = "";
  private String carrier = "AA";
  private String returnPlusNumberOfDays = "";
  private String returnTime = "";
  private String connectCity = "";

  private Flight() {
  }

  public String getOrigin() {
    return origin;
  }

  public String getDestination() {
    return destination;
  }

  public String getDepartingNoOfDaysFromToday() {
    return departingNoOfDaysFromToday;
  }

  public String getDepartTime() {
    return departTime;
  }

  public String getCarrier() {
    return carrier;
  }

  public String getReturnPlusNumberOfDays() {
    return returnPlusNumberOfDays;
  }

  public String getReturnTime() { return returnTime; }

  public String getConnectCity() {
    return connectCity;
  }

  public static class FlightBuilder {

    private Flight flight = new Flight();

    public FlightBuilder withOrigin(String origin) {
      this.flight.origin = origin;
      return this;
    }

    public FlightBuilder withDestination(String destination) {
      this.flight.destination = destination;
      return this;
    }

    public FlightBuilder withDepartingNoOfDaysFromToday(String departingNoOfDaysFromToday) {

      if(StringUtils.isNotEmpty(departingNoOfDaysFromToday)) {
        this.flight.departingNoOfDaysFromToday = departingNoOfDaysFromToday;
      }
      return this;
    }

    public FlightBuilder withDepartTime(String departTime) {

      if(StringUtils.isNotEmpty(departTime)) {
        this.flight.departTime = departTime;
      }
      return this;
    }

    public FlightBuilder withCarrier(String carrier) {

      if(StringUtils.isNotEmpty(carrier)) {
        this.flight.carrier = carrier;
      }
      return this;
    }

    public FlightBuilder withReturnPlusNumberOfDays(String returnPlusNumberOfDays) {

      if(StringUtils.isNotEmpty(returnPlusNumberOfDays)) {
        this.flight.returnPlusNumberOfDays = returnPlusNumberOfDays;
      }
      return this;
    }

    public FlightBuilder withReturnTime(String returnTime) {

      if(StringUtils.isNotEmpty(returnTime)) {
        this.flight.returnTime = returnTime;
      }
      return this;
    }

    public FlightBuilder withConnectCity(String connectCity) {

      if(StringUtils.isNotEmpty(connectCity)) {
        this.flight.connectCity = connectCity;
      }
      return this;
    }

    public Flight build() {
      return flight;
    }

  }

}