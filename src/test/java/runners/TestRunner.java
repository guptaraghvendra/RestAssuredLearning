package runners;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * @author
 * @author Last updated by : $
 * @version : $
 */
@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/functionalTests",glue = {"stepDefinitions"},monochrome = true)
public class TestRunner {
}
