package com.cobrand.ct.action;

import static com.cobrand.ct.config.AARestAssuredConfiguration.aaCobrandSpec;
import static net.serenitybdd.rest.SerenityRest.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cobrand.ct.utils.ActionUtils;

import io.restassured.response.ValidatableResponse;

@Component
public class CobrandAction {
    private final ActionUtils actionUtils = new ActionUtils();

    public String getCobrandCitiAd(boolean isCheckinPath, String deviceType, String locale, String recordLocator) {

        if (isCheckinPath) {
            return getCobrandCitiContent(deviceType, locale, recordLocator);
        }

        return null;

    }

    private String getCobrandCitiContent(String deviceType, String locale, String recordLocator) {

        Map<String, String> params = new HashMap<>();
        params.put("deviceType", deviceType);
        params.put("locale", locale);
        params.put("recordLocator", recordLocator);

        ValidatableResponse response = rest()
                .spec(aaCobrandSpec())
                .params(params)
                .log().all()
                .when()
                .get("/api")
                .then()
                .log().all();
       if(response==null)
       {
    	   return null;
       }
        actionUtils.verifyServiceAvailability(response);

        return response.extract().response().asString();
    }
}
