package features.common.steps;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.cobrand.ct.CobrandAcceptanceApplication;
import com.cobrand.ct.action.CobrandAction;
import com.cobrand.ct.action.PassengerAction;
import com.cobrand.ct.action.PassengerEligibilityAction;

import com.cobrand.ct.utils.ActionUtils;
@SpringBootTest(classes = CobrandAcceptanceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AppConfiguration {
	@Bean
    public ActionUtils actionUtils() {
        return new ActionUtils();
    }

	@Bean
    public CobrandAction cobrandAction() {
        return new CobrandAction();
    }
	@Bean
    public PassengerAction passengerAction() {
        return new PassengerAction();
    }
	
	@Bean
    public PassengerEligibilityAction passengerEligibilityAction() {
        return new PassengerEligibilityAction();
    }
	
}