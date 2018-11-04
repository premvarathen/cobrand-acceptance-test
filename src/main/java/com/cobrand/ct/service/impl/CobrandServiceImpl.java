package com.cobrand.ct.service.impl;

import static com.cobrand.ct.config.AARestAssuredConfiguration.aaCobrandSpec;
import static io.restassured.path.json.JsonPath.from;
import static net.serenitybdd.rest.SerenityRest.rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.assertj.core.api.SoftAssertions;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cobrand.ct.domain.Reservation;
import com.cobrand.ct.mapper.ReservationMapper;
import com.cobrand.ct.sabre.VelocityTemplateDeterminer;
import com.cobrand.ct.service.CobrandService;
import com.cobrand.ct.utils.ActionUtils;

import aa.ct.fly.bdd.pnr.compromisedException.PnrCouldNotBeCreated;
import aa.ct.fly.bdd.pnr.compromisedException.SabreCommandsCouldNotBeCreated;
import aa.ct.fly.bdd.pnr.config.AARestAssuredConfiguration;
import aa.ct.fly.bdd.pnr.pnrcreation.PNRToolVersion;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

@Service
public class CobrandServiceImpl implements CobrandService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CobrandServiceImpl.class);
	@Autowired
	private VelocityEngine engine;
	
	@Autowired
	private ActionUtils actionUtils;
	@Override
	public String createPNR( List<Map<String, String>> reservationRawData) {
		// TODO Auto-generated method stub
		 ReservationMapper reservationMapper = new ReservationMapper();
		  Reservation reservation = reservationMapper.map(reservationRawData);
		  
		
		String commands  = generator(reservation);
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

    
    public String generator(Reservation reservation) {

        VelocityContext context = setVelocityContext(reservation);
        Template template = getTemplate(reservation);

        StringWriter sw = new StringWriter();
        template.merge(context, sw);

        return sw.toString();
      }
      
     
      

      private VelocityContext setVelocityContext(Reservation reservation) {
        VelocityContext context = new VelocityContext();
        context.put("pnr", reservation);
        return context;
      }
      
      private Template getTemplate(Reservation reservation) {
    	   VelocityTemplateDeterminer templateDeterminer = new VelocityTemplateDeterminer();

    	    String templateFile = templateDeterminer.determine(reservation);
    	    Template template;

    	    try {
    	      template = engine.getTemplate(templateFile);
    	    }
    	    catch(ResourceNotFoundException rnfe) {
    	      LOGGER.error("couldn't find the template {}", templateFile);
    	      throw new SabreCommandsCouldNotBeCreated("couldn't find the template " + templateFile);
    	    }
    	    catch(ParseErrorException pee) {
    	      LOGGER.error("syntax error: problem parsing the template");
    	      throw new SabreCommandsCouldNotBeCreated("syntax error: problem parsing the template");

    	    }
    	    catch(MethodInvocationException mie) {
    	      LOGGER.error("something invoked in the template threw an exception");
    	      throw new SabreCommandsCouldNotBeCreated("something invoked in the template threw an exception");

    	    }
    	    catch(Exception e) {
    	      LOGGER.error(e.getMessage());
    	      throw new SabreCommandsCouldNotBeCreated("something invoked in the template threw an exception");

    	    }
    	    return template;
    	  }

	@Override
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

	@Override
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


