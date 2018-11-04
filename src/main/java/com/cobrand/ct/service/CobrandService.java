/**
 * 
 */
package com.cobrand.ct.service;

import java.util.List;
import java.util.Map;

import io.restassured.response.ValidatableResponse;

/**
 * @author PremVarathen Ramasundaram
 *
 */
public interface CobrandService {

	public String createPNR(List<Map<String, String>> reservation);
	public String getCobrandCitiAd(boolean isCheckinPath, String deviceType, String locale, String recordLocator);
	public void validatesBagsProductEligible(ValidatableResponse response);

}
