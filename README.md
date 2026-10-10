# 📱 Appium Mobile Test Framework

[![CI](https://github.com/IslamHassan94/appium-mobile-test-framework/actions/workflows/ci.yml/badge.svg)](https://github.com/IslamHassan94/appium-mobile-test-framework/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk&logoColor=white)
![Appium](https://img.shields.io/badge/Appium-9.x-662D91?logo=appium&logoColor=white)
![Cucumber](https://img.shields.io/badge/Cucumber-BDD-23D96C?logo=cucumber&logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-7-FF7F00)

A **BDD mobile test automation framework** for Android, built with **Java, Appium, Cucumber and TestNG**.
It tests the open-source [Sauce Labs My Demo App](https://github.com/saucelabs/my-demo-app-rn) (a React Native shopping app) across login, catalog sorting and the shopping cart, and runs **locally, on an emulator in GitHub Actions, or on BrowserStack real devices** without code changes.

---

## 🧰 Tech stack

| Area | Tools |
|---|---|
| Language | Java 17 |
| Mobile automation | Appium java-client 9 (UiAutomator2) |
| BDD | Cucumber 7 (Gherkin), PicoContainer for dependency injection |
| Test runner | TestNG (parallel-ready data provider) |
| Assertions | AssertJ |
| Reporting | Cucumber HTML/JSON, Allure, screenshot and page source on failure |
| CI/CD | GitHub Actions (compile on every push, Android emulator run on demand) |
| Device cloud | BrowserStack App Automate (optional) |

## 🏗️ Framework design

```
src/test
├── java/com/islam/mobile
│   ├── config/Config.java          # properties with -D and environment variable overrides
│   ├── driver/DriverFactory.java   # ThreadLocal AndroidDriver: local or BrowserStack
│   ├── pages/                      # Page Objects (BasePage, AppHeader, Menu, Login, Products, ProductDetails, Cart)
│   ├── steps/                      # step definitions per feature area
│   ├── context/ScenarioContext.java# state shared between steps within one scenario
│   ├── hooks/Hooks.java            # app start/stop, screenshot + page source on failure
│   └── runners/TestRunner.java     # Cucumber + TestNG runner
└── resources
    ├── features/                   # login, catalog, cart (Gherkin)
    ├── config.properties
    └── testng.xml
```

**Key decisions**

- **Page Object Model with a `BasePage`:** every interaction goes through explicit waits (`tap`, `type`, `visible`, `scrollToText`). No `Thread.sleep`, no implicit waits.
- **Stable locators:** accessibility IDs first (the app's test IDs), text selectors only for user-visible messages.
- **ThreadLocal driver:** each scenario gets its own session, so scenarios can run in parallel across devices.
- **Configuration without code changes:** any setting in `config.properties` can be overridden with `-Dkey=value` or an environment variable (`device.name` → `DEVICE_NAME`).
- **Dependency injection:** PicoContainer shares a fresh `ScenarioContext` between step classes for every scenario, so tests never leak state.
- **Debuggable failures:** a screenshot and the full page source are attached to the report when a scenario fails.

## ✅ Test coverage

| Feature | Scenarios |
|---|---|
| **Login** | Valid login, locked-out user, unknown user, missing username, missing password |
| **Catalog** | Sort by name and price (both directions), open product details and check name and price |
| **Cart** | Badge updates after adding, quantity × unit price equals cart total (data-driven), removing the last item empties the cart |

Scenarios are tagged `@smoke`, `@login`, `@catalog` and `@cart`.

## 🚀 Getting started

**Prerequisites:** JDK 17, Maven, Node.js, Android SDK with an emulator, and Appium 2:

```bash
npm install -g appium
appium driver install uiautomator2
```

**Run the tests:**

```bash
appium                                    # start the Appium server (port 4723)
mvn clean test                            # run everything
mvn clean test -Dcucumber.filter.tags="@smoke"
mvn clean test -Ddevice.name="Pixel_8" -Dplatform.version=14
mvn clean test -Ddataproviderthreadcount=2  # parallel, needs two devices
```

**Run on BrowserStack:**

```bash
export BROWSERSTACK_USERNAME=... BROWSERSTACK_ACCESS_KEY=...
mvn clean test -Drun.on=browserstack -Dbrowserstack.app=bs://<app-id>
```

## 📊 Reports

- Cucumber HTML: `target/cucumber-report.html`
- Allure: `mvn allure:serve`

## 👤 Author

**Islam Hassan**, Senior SDET · [LinkedIn](https://www.linkedin.com/in/islam-hassan-5366b9180/) · [GitHub](https://github.com/IslamHassan94)
