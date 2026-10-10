package com.islam.mobile.hooks;

import com.islam.mobile.driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;

public class Hooks {

    @Before(order = 0)
    public void startApp() {
        DriverFactory.startDriver();
    }

    @After(order = 0)
    public void closeApp(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                byte[] png = DriverFactory.getDriver().getScreenshotAs(OutputType.BYTES);
                scenario.attach(png, "image/png", scenario.getName());
                scenario.attach(DriverFactory.getDriver().getPageSource(), "text/xml", "page-source");
            }
        } finally {
            DriverFactory.quitDriver();
        }
    }
}
