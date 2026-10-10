package com.islam.mobile.pages;

import com.islam.mobile.config.Config;
import com.islam.mobile.driver.DriverFactory;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/** Common, wait-based interactions. Page objects never call driver.findElement directly. */
public abstract class BasePage {
    protected final AndroidDriver driver;
    protected final WebDriverWait wait;

    protected BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.getInt("explicit.wait")));
    }

    /** Each page knows how to tell that it is displayed. */
    public abstract boolean isDisplayed();

    protected static By id(String accessibilityId) {
        return AppiumBy.accessibilityId(accessibilityId);
    }

    protected static By text(String text) {
        return AppiumBy.androidUIAutomator("new UiSelector().text(\"" + text + "\")");
    }

    protected WebElement visible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected List<WebElement> allVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    protected void tap(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String value) {
        WebElement field = visible(locator);
        field.clear();
        if (value != null && !value.isEmpty()) {
            field.sendKeys(value);
        }
    }

    protected String textOf(By locator) {
        return visible(locator).getText().trim();
    }

    protected boolean isPresent(By locator) {
        try {
            visible(locator);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** Scrolls the first scrollable container until an element with the given text is in view. */
    protected WebElement scrollToText(String value) {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))"
                        + ".scrollIntoView(new UiSelector().text(\"" + value + "\"))"));
    }
}
