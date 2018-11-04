package com.cobrand.ct.service.impl;

import static net.serenitybdd.rest.SerenityRest.rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.cobrand.ct.controller.ReservationWrapper;
import com.cobrand.ct.sabre.CobrandSabrePnrCreationProvider;
import com.cobrand.ct.service.CobrandService;
import com.fasterxml.jackson.databind.ObjectMapper;

import aa.ct.fly.bdd.pnr.compromisedException.PnrCouldNotBeCreated;
import aa.ct.fly.bdd.pnr.config.AARestAssuredConfiguration;
import aa.ct.fly.bdd.pnr.pnrcreation.PNRToolVersion;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@Service
public class CobrandServiceImpl implements CobrandService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CobrandServiceImpl.class);
	@Autowired
	private VelocityEngine engine;
	
	@Override
	public String createPNR(ReservationWrapper wrapper) {
		// TODO Auto-generated method stub
		
		ObjectMapper oMapper = new ObjectMapper();
		Map<String, Object> map = oMapper.convertValue(wrapper, Map.class);
		
		String commands =VelocityEngineUtils.mergeTemplateIntoString(this.engine,
				"cobrand_pnr_non_stop.vm", "UTF-8", map);
		 RequestSpecification spec = new AARestAssuredConfiguration().aaPnrSpec(PNRToolVersion.V1);
		 String pnr = "";
	        try {
	            if (commands != null) {
	                Response response = doPost(getRestCaller(spec, commands, "test"));

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
    
    // Seam for unit testing
    Response doPost(RequestSpecification requestSpecification) {
        return requestSpecification.post("sabrepnr/data");
    }

    // Seam for unit testing
    Response doGet(RequestSpecification requestSpecification) {
        return requestSpecification.post("sabrepnr/delete");
    }

}
