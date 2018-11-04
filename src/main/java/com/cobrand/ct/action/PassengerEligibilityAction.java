package com.cobrand.ct.action;

import static com.cobrand.citi.constant.CobrandConstants.ELIG_RESOURCE_ROOT;
import static com.cobrand.ct.config.AARestAssuredConfiguration.aaMerchSpec;
import static io.restassured.path.json.JsonPath.from;
import static net.serenitybdd.rest.SerenityRest.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.springframework.stereotype.Component;

import com.cobrand.ct.utils.ActionUtils;

import io.restassured.response.ValidatableResponse;

@Component
public class PassengerEligibilityAction {
    private final ActionUtils actionUtils = new ActionUtils();

    public ValidatableResponse passengerChecksBagsEligibility(String recordLocator) {

        Map<String, String> params = new HashMap<>();
        params.put("recordLocator", recordLocator);
        return checkEligibility(params);
    }

    private ValidatableResponse checkEligibility(Map<String, String> params) {

        ValidatableResponse response = rest()
                .spec(aaMerchSpec())
                .header("x-clientId", "AACOM")
                .header("Authorization", actionUtils.generateToken())
                .params(params)
                .log().all()
                .when()
                .get(ELIG_RESOURCE_ROOT)
                .then()
                .log().all();

        actionUtils.verifyServiceAvailability(response);
        return response;
    }

    public void validatesBagsProductEligible(ValidatableResponse response) {
        String responseAsString = response.extract().response().asString();
        List<Map<String, ?>> flightInfos = from(responseAsString).get("flightInfos");

        boolean anyFailedRules = flightInfos.stream().anyMatch(flightInfo -> (!((List<String>) flightInfo.get("failedRules")).isEmpty()));

        SoftAssertions assertions = new SoftAssertions();
        assertions
                .assertThat(anyFailedRules)
                .as("there was prepaid bags eligibility failed rules")
                .isEqualTo(false);
        assertions.assertAll();
    }
}
