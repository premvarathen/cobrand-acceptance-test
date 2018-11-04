package com.cobrand.ct.sabre;


import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
@RunWith(CucumberWithSerenity.class)

//@CucumberOptions(features={"src/test/resources/"},glue ={"features.common.steps","features.eligibility.steps","features.eligibility.rules.steps","features.content.steps"},plugin = {"pretty"})
//@CucumberOptions(features={"src/test/resources/"},glue ={"features.common.steps","features.content.steps"},plugin = {"pretty"})

@CucumberOptions(features={"src/test/resources/"},glue ={"features.eligibility.rules.steps",} ,plugin = {"pretty"})
//@CucumberOptions(features={"src/test/resources/features/eligibilityRules.feature"},glue ={"features.eligibility.rules.steps",} ,plugin = {"pretty"})
public class CobrandMainRunner {

	

}
