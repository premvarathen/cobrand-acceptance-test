package features.eligibility.rules.steps;

import static com.cobrand.citi.constant.CobrandConstants.SESSION_KEY_RECORD_LOCATOR;
import static com.cobrand.citi.constant.CobrandConstants.SESSION_KEY_RETRY_PNR_COUNTER;
import static io.restassured.path.json.JsonPath.from;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.cobrand.ct.action.CobrandAction;
import com.cobrand.ct.action.PassengerAction;
import com.cobrand.ct.domain.Passenger;
import com.cobrand.ct.service.CobrandService;
import com.cobrand.ct.utils.ActionUtils;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import features.common.steps.AppConfiguration;
import net.serenitybdd.core.Serenity;
@ContextConfiguration(classes= AppConfiguration.class)
public class EligibilityRulesStepDefinition{

	private boolean isPnrCreated;
	private String deviceType = null;
	private String locale = null;
	private String actualResult;
	private String recordLocator;

	public EligibilityRulesStepDefinition() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	ActionUtils actionUtils ;
	@Autowired
	PassengerAction passengerAction;
	@Autowired
	private CobrandAction action ;
	
	@Autowired
	CobrandService cobrandService;

	@Given("^passenger booking a flight ticket$")
	public void passenger_booking_a_flight_ticket(List<Map<String, String>> reservationRawData) throws Throwable {
		this.recordLocator = actionUtils.invokeWithRetry(() -> passengerAction.passengerRequestsPNR(reservationRawData),
				SESSION_KEY_RETRY_PNR_COUNTER);
		Serenity.setSessionVariable(SESSION_KEY_RECORD_LOCATOR).to(this.recordLocator);
	}

	@Given("^for the locale  \"([^\"]*)\" and deviceType as \"([^\"]*)\"$")
	public void for_the_locale_and_deviceType_as(String locale, String deviceType) throws Exception {
		// Write code here that turns the phrase above into concrete actions
		this.locale = locale;
		this.deviceType = deviceType;
	}

	@When("^the pnr is created$")
	public void when() throws Exception {
		this.isPnrCreated = StringUtils.isNotBlank(this.recordLocator);
		assertTrue(isPnrCreated);
	}

	@Then("^check the eligibility rules for the Passenger and expect a response$")
	public void check_the_eligibility_rules_for_the_Passenger_and_expect_a_response(DataTable dataTable) throws Exception {

		this.actualResult = action.getCobrandCitiAd(this.isPnrCreated, this.deviceType, this.locale, this.recordLocator);
		//check the content
		 Map<String,String> offerMap = from(this.actualResult).get("offer");
	     assertEquals("Get your first checked bag fee waived on domestic trips", offerMap.get("offerHeading"));
	        
		List<Map<String,String>> passengerMap = from(this.actualResult).getList("passengerList");
		Passenger passenger = dataTable.transpose().asList(Passenger.class).get(0);
		Map<String,String> result = passengerMap.get(0);
		assertEquals(passenger.getFirstName().toLowerCase(), result.get("firstName").toLowerCase());
		assertEquals(passenger.getLastName().toLowerCase(), result.get("lastName").toLowerCase());
		//assertEquals(passenger.getDateOfBirth().toLowerCase(), result.get("dateOfBirth").toLowerCase());
	    
	}

	@Then("^the eligibility rules fail and the response would not have any passengers\\.$")
	public void the_eligibility_rules_fail_and_the_response_would_not_have_any_passengers() throws Exception {
		this.actualResult = action.getCobrandCitiAd(this.isPnrCreated, this.deviceType, this.locale,
				this.recordLocator);
		if(actualResult!=null && !actualResult.trim().isEmpty())
		{
			List<Map<String,String>> passengerMap = from(this.actualResult).getList("passengerList");
			assertNull(passengerMap);
		}
	}

	@Then("^check the eligigbility rules for the mutilple Passenger's and expect a response$")
	public void check_the_eligibility_rules_for_the_mutilple_Passengers_and_expect_a_response() throws Exception {

		this.actualResult = action.getCobrandCitiAd(this.isPnrCreated, this.deviceType, this.locale, this.recordLocator);
		List<Map<String,String>> passengerMap = from(this.actualResult).getList("passengerList");
		assertThat(passengerMap.size()==2);
	    
	}
	
	@After
	public void tearDown() {
		//passengerAction.deletePnr(Serenity.sessionVariableCalled(SESSION_KEY_RECORD_LOCATOR));
	}

}
