package com.cobrand.ct.utils;

import static com.cobrand.citi.constant.CobrandConstants.CLIENT_CREDENTIALS;
import static com.cobrand.citi.constant.CobrandConstants.IS_RETRY_ENABLED;
import static com.cobrand.citi.constant.CobrandConstants.JWT_PPB_CLIENT;
import static com.cobrand.citi.constant.CobrandConstants.JWT_RESOURCE_ROOT;
import static com.cobrand.citi.constant.CobrandConstants.JWT_SECRET_KEY;
import static com.cobrand.citi.constant.CobrandConstants.MAX_RETRY;
import static com.cobrand.citi.constant.CobrandConstants.SESSION_KEY_AUTHORIZATION;
import static com.cobrand.ct.config.AARestAssuredConfiguration.aaJWTSpec;
import static net.serenitybdd.rest.SerenityRest.rest;

import java.util.function.Supplier;

import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import aa.ct.fly.bdd.pnr.compromisedException.PnrCouldNotBeCreated;
import common.compromisedException.AuthorizationTokenNotAvailable;
import common.compromisedException.MaximumNoOfRetriesExceeded;
import common.compromisedException.ResultedServiceCallFailureWithNon200StatusCode;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.Serenity;
@Component
public class ActionUtils {

    private static final String MAX_RETRY_PROPERTY = "max_retry";
    private static final String RETRY_ENABLED_PROPERTY = "retry_enabled";
    private final int maxRetry;
    private final boolean retryEnabled;

    public ActionUtils() {
        String max_retry_prop = System.getProperty(MAX_RETRY_PROPERTY);
        String retry_enabled_prop = System.getProperty(RETRY_ENABLED_PROPERTY);

        maxRetry = StringUtils.isEmpty(max_retry_prop) ? MAX_RETRY : Integer.valueOf(max_retry_prop);
        retryEnabled = StringUtils.isEmpty(retry_enabled_prop) ? IS_RETRY_ENABLED : Boolean.valueOf(retry_enabled_prop);
    }

    public void verifyServiceAvailability(ValidatableResponse response) {
        if (response.extract().statusCode() != HttpStatus.SC_OK) {
            throw new ResultedServiceCallFailureWithNon200StatusCode("Service Unavailable");
        }
    }

    public String generateToken() {

        String authorization = Serenity.sessionVariableCalled(SESSION_KEY_AUTHORIZATION);
        if (!StringUtils.isEmpty(authorization)) {
            return authorization;
        }

        ValidatableResponse response = rest()
                .spec(aaJWTSpec())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "application/json")
                .body(buildParams())
                .log().all()
                .when()
                .post(JWT_RESOURCE_ROOT)
                .then()
                .log().all();

        verifyServiceAvailability(response);

        String accessToken = response.extract().body().jsonPath().get("access_token");
        if (!StringUtils.isEmpty(accessToken)) {
            authorization = String.format("Bearer %s", accessToken);
            Serenity.setSessionVariable(SESSION_KEY_AUTHORIZATION).to(authorization);
        } else {
            throw new AuthorizationTokenNotAvailable();
        }
        return authorization;
    }

    private String buildParams() {
        return String.format("client_id=%s&client_secret=%s&grant_type=%s", JWT_PPB_CLIENT, JWT_SECRET_KEY, CLIENT_CREDENTIALS);
    }

    public <R> R invokeWithRetry(Supplier<R> supplier, String sessionKeyRetryCounter) {
        R validationResponse;
        try {
            validationResponse = supplier.get();
        } catch (ResultedServiceCallFailureWithNon200StatusCode | PnrCouldNotBeCreated ex) {
            checkRetryEnabled(ex);
            adjustRetryCounter(sessionKeyRetryCounter);
            validationResponse = invokeWithRetry(supplier, sessionKeyRetryCounter);
        }

        resetRetryCounter(sessionKeyRetryCounter);
        return validationResponse;
    }

    private void resetRetryCounter(String sessionKeyRetryCounter) {
        Serenity.setSessionVariable(sessionKeyRetryCounter).to(0);
    }

    private void checkRetryEnabled(RuntimeException ex) {
        if (!retryEnabled) {
            throw ex;
        }
    }

    private void adjustRetryCounter(String sessionKeyRetryCounter) {
        if (Serenity.sessionVariableCalled(sessionKeyRetryCounter) == null) {
            Serenity.setSessionVariable(sessionKeyRetryCounter).to(1);
        } else {
            Integer counter = Serenity.sessionVariableCalled(sessionKeyRetryCounter);
            if (counter == maxRetry - 1) {
                throw new MaximumNoOfRetriesExceeded();
            } else {
                Serenity.setSessionVariable(sessionKeyRetryCounter).to(++counter);
            }
        }
    }
}
