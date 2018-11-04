package features.eligibility.steps;

import static com.cobrand.citi.constant.CobrandConstants.SESSION_KEY_RECORD_LOCATOR;

import com.cobrand.ct.action.PassengerAction;

import cucumber.api.java.en.And;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.core.Serenity;

public class SampleStepsDefinition{

    private PassengerAction passengerAction = new PassengerAction();

    @And("^the Passenger is Eligible For Bag Product$")
    public void thePassengerIsEligibleForBagProduct() {
        ValidatableResponse response = passengerAction.passengerChecksBagsEligibility(Serenity.sessionVariableCalled(SESSION_KEY_RECORD_LOCATOR));
        passengerAction.validatesAndContinueOnlyIfBagsProductEligible(response);
    }
}
