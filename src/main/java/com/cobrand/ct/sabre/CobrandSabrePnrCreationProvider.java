package com.cobrand.ct.sabre;

import static net.serenitybdd.rest.SerenityRest.rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import com.cobrand.ct.controller.ReservationWrapper;
import com.cobrand.ct.domain.Reservation;
import com.cobrand.ct.generator.CommandGenerator;
import com.cobrand.ct.mapper.ReservationMapper;

import aa.ct.fly.bdd.pnr.compromisedException.PnrCouldNotBeCreated;
import aa.ct.fly.bdd.pnr.config.AARestAssuredConfiguration;
import aa.ct.fly.bdd.pnr.pnrcreation.PNRToolVersion;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;

public class CobrandSabrePnrCreationProvider {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CobrandSabrePnrCreationProvider.class);
    private ReservationMapper reservationMapper = new ReservationMapper();
    private CommandGenerator commandGenerator = new CommandGenerator();

    public String createWithRawData(List<Map<String, String>> reservationRawData, String url) {
        RequestSpecification spec = new AARestAssuredConfiguration().aaPnrSpec(url);
        return createWithSpecAndRawData(spec, reservationRawData);
    }

    @Deprecated
    public String createWithRawData(List<Map<String, String>> reservationRawData, PNRToolVersion version, String url) {
        return createWithRawData(reservationRawData, url);
    }

    public String createWithRawData(List<Map<String, String>> reservationRawData, PNRToolVersion version) {
        RequestSpecification spec = new AARestAssuredConfiguration().aaPnrSpec(version);
        return createWithSpecAndRawData(spec, reservationRawData);
    }

    String createWithSpecAndRawData(RequestSpecification spec, List<Map<String, String>> reservationRawData) {
        Reservation reservation = reservationMapper.map(reservationRawData);
        return createWithCommands(spec, reservation);
    }

    public void deletePnr(String recordLocator, boolean isConfirmed, String url) {
        RequestSpecification spec = new AARestAssuredConfiguration().aaPnrSpec(url);
        deletePnrWithSpec(spec, recordLocator, isConfirmed);
    }

    public void deletePnr(String recordLocator, boolean isConfirmed, PNRToolVersion version) {
        RequestSpecification spec = new AARestAssuredConfiguration().aaPnrSpec(version);
        deletePnrWithSpec(spec, recordLocator, isConfirmed);
    }

    void deletePnrWithSpec(RequestSpecification spec, String recordLocator, boolean isConfirmed) {
        if (isConfirmed) {
            try {
                if (recordLocator != null) {
                    Response response = doGet(rest()
                            .spec(spec)
                            .param("pnr", recordLocator)
                            .log().all()
                            .auth().basic("admin", "admin")
                            .when());

                    int statusCode = response.statusCode();
                    if (statusCode != 200) {
                        LOGGER.debug("Sabre PNR Service Could Not Delete RecordLocator {}: and returned status as {}: ",
                                recordLocator, statusCode);
                    }
                }
            } catch (Exception ex) {
                LOGGER.debug("Exception Thrown While Deleting PNR: " + ex.getMessage());
            }
        }
    }

    @Step
    public String createWithCommands(Reservation reservation, PNRToolVersion version) {
        RequestSpecification spec = new AARestAssuredConfiguration().aaPnrSpec(version);
        return createWithCommands(spec, reservation);
    }

    @Step
    public String createWithCommandsForWrapper(ReservationWrapper reservation, PNRToolVersion version) {
        RequestSpecification spec = new AARestAssuredConfiguration().aaPnrSpec(version);
        return createWithCommands(spec, reservation);
    }
    
    String createWithCommands(RequestSpecification spec, Reservation reservation) {
        String commands = commandGenerator.generator(reservation);
        String pnr = "";
        try {
            if (commands != null) {
                Response response = doPost(getRestCaller(spec, commands, reservation.getTestId()));

                int statusCode = response.statusCode();

                if (statusCode == 200) {
                    pnr = response
                            .body()
                            .path("pnr");
                } else {
                    throw new PnrCouldNotBeCreated(
                            "Sabre PNR Service Call Returned Exception: " + "Sabre Call returned with Status Code"
                                    + statusCode);
                }

            }
        } catch (Exception ex) {
            LOGGER.debug("Exception Thrown: " + ex.getMessage());
            throw new PnrCouldNotBeCreated("Sabre PNR Service Call Returned Exception: " + convertExceptionToString(ex),
                    ex);
        }
        if (StringUtils.isEmpty(pnr)) {
            LOGGER.debug("Sabre PNR Service Call Was Successful, but PNR Could not be created.");
            throw new PnrCouldNotBeCreated("Sabre PNR Service Call Was Successful, but PNR Could not be created.");
        }

        return pnr;
    }

    

    String createWithCommands(RequestSpecification spec, ReservationWrapper reservation) {
        String commands = commandGenerator.generator(reservation);
        String pnr = "";
        try {
            if (commands != null) {
                Response response = doPost(getRestCaller(spec, commands, reservation.getTestId()));

                int statusCode = response.statusCode();

                if (statusCode == 200) {
                    pnr = response
                            .body()
                            .path("pnr");
                } else {
                    throw new PnrCouldNotBeCreated(
                            "Sabre PNR Service Call Returned Exception: " + "Sabre Call returned with Status Code"
                                    + statusCode);
                }

            }
        } catch (Exception ex) {
            LOGGER.debug("Exception Thrown: " + ex.getMessage());
            throw new PnrCouldNotBeCreated("Sabre PNR Service Call Returned Exception: " + convertExceptionToString(ex),
                    ex);
        }
        if (StringUtils.isEmpty(pnr)) {
            LOGGER.debug("Sabre PNR Service Call Was Successful, but PNR Could not be created.");
            throw new PnrCouldNotBeCreated("Sabre PNR Service Call Was Successful, but PNR Could not be created.");
        }

        return pnr;
    }


    // Seam for unit testing
    Response doPost(RequestSpecification requestSpecification) {
        return requestSpecification.post("sabrepnr/data");
    }

    // Seam for unit testing
    Response doGet(RequestSpecification requestSpecification) {
        return requestSpecification.post("sabrepnr/delete");
    }

    RequestSpecification getRestCaller(RequestSpecification spec, String commands, String testId) {
        return rest()
                .spec(spec)
                .log().all()
                .auth().basic("admin", "admin")
                .body(commands)
                .header("X-testId", testId)
                .when();
    }

    private String convertExceptionToString(Exception ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }
}