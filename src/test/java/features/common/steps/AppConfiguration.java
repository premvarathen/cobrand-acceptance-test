package features.common.steps;

import java.io.IOException;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.velocity.VelocityEngineFactory;

import com.cobrand.ct.CobrandAcceptanceApplication;
import com.cobrand.ct.action.CobrandAction;
import com.cobrand.ct.action.PassengerAction;
import com.cobrand.ct.action.PassengerEligibilityAction;
import com.cobrand.ct.service.CobrandService;
import com.cobrand.ct.service.impl.CobrandServiceImpl;
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
	
	@Bean
    public CobrandService cobrandService() {
        return new CobrandServiceImpl();
    }
	
	@Bean
	public VelocityEngine getVelocityEngine() throws VelocityException, IOException{
        VelocityEngineFactory factory = new VelocityEngineFactory();
        Properties props = new Properties();
        props.put("resource.loader", "class");
        props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        factory.setVelocityProperties(props);
        return factory.createVelocityEngine();      
    }
	
}