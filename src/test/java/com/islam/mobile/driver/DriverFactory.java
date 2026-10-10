package com.islam.mobile.driver;

import com.islam.mobile.config.Config;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/** Creates one AndroidDriver per thread, so scenarios can run in parallel on several devices. */
public final class DriverFactory {
    private static final ThreadLocal<AndroidDriver> DRIVER = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static AndroidDriver getDriver() {
        AndroidDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("Driver not started. Is the @Before hook running?");
        }
        return driver;
    }

    public static void startDriver() {
        AndroidDriver driver = Config.isBrowserStack() ? browserStackDriver() : localDriver();
        DRIVER.set(driver);
    }

    public static void quitDriver() {
        AndroidDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }

    private static AndroidDriver localDriver() {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(Config.get("platform.name"))
                .setDeviceName(Config.get("device.name"))
                .setPlatformVersion(Config.get("platform.version"))
                .setAutomationName(Config.get("automation.name"))
                .setApp(Path.of(Config.get("app.path")).toAbsolutePath().toString())
                .setAppWaitActivity("*")
                .setAutoGrantPermissions(true)
                .setNewCommandTimeout(Duration.ofSeconds(Config.getInt("new.command.timeout")));
        return new AndroidDriver(url(Config.get("appium.url")), options);
    }

    private static AndroidDriver browserStackDriver() {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setApp(Config.get("browserstack.app"));
        Map<String, Object> bstack = new HashMap<>();
        bstack.put("userName", System.getenv("BROWSERSTACK_USERNAME"));
        bstack.put("accessKey", System.getenv("BROWSERSTACK_ACCESS_KEY"));
        bstack.put("deviceName", Config.get("browserstack.device"));
        bstack.put("osVersion", Config.get("browserstack.os.version"));
        bstack.put("projectName", "Appium Mobile Test Framework");
        bstack.put("buildName", System.getenv().getOrDefault("GITHUB_RUN_ID", "local"));
        options.setCapability("bstack:options", bstack);
        return new AndroidDriver(url("https://hub.browserstack.com/wd/hub"), options);
    }

    private static URL url(String value) {
        try {
            return URI.create(value).toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid Appium URL: " + value, e);
        }
    }
}
