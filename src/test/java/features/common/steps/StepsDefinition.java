package features.common.steps;

import static com.cobrand.citi.constant.CobrandConstants.SESSION_KEY_RECORD_LOCATOR;
import static com.cobrand.citi.constant.CobrandConstants.SESSION_KEY_RETRY_PNR_COUNTER;

import java.util.List;
import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.springframework.test.context.ContextConfiguration;

import com.cobrand.citi.constant.CobrandConstants;
import com.cobrand.ct.action.PassengerAction;
import com.cobrand.ct.utils.ActionUtils;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import net.serenitybdd.core.Serenity;

@ContextConfiguration(classes= AppConfiguration.class)
public class StepsDefinition {
    PassengerAction passengerAction = new PassengerAction();
    ActionUtils actionUtils = new ActionUtils();

    @Given("^Reservation$")
    public void reservation(List<Map<String, String>> reservationRawData) throws Throwable {
        String recordLocator = actionUtils.invokeWithRetry(() -> passengerAction.passengerRequestsPNR(reservationRawData), SESSION_KEY_RETRY_PNR_COUNTER);
    	Serenity.setSessionVariable(SESSION_KEY_RECORD_LOCATOR).to(recordLocator);
    }

    @After
    public void tearDown() {
        passengerAction.deletePnr(Serenity.sessionVariableCalled(SESSION_KEY_RECORD_LOCATOR));
    }

    @When("^the user has a reservation$")
    public void theUserHasAReservation() throws Throwable {
        SoftAssertions assertions = new SoftAssertions();
        assertions
                .assertThat((String) Serenity.sessionVariableCalled(CobrandConstants.SESSION_KEY_RECORD_LOCATOR))
                .as("reservation was not created")
                .isNotEqualTo(null);
        assertions.assertAll();
    }
}
