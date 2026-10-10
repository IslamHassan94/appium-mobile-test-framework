package com.islam.mobile.steps;

import com.islam.mobile.pages.AppHeader;
import com.islam.mobile.pages.LoginPage;
import com.islam.mobile.pages.ProductsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginSteps {

    @Given("the app is launched on the catalog")
    public void theAppIsLaunched() {
        assertThat(new ProductsPage().isDisplayed()).as("catalog screen is displayed").isTrue();
    }

    @Given("I open the login screen")
    public void iOpenTheLoginScreen() {
        LoginPage login = new AppHeader().openMenu().goToLogin();
        assertThat(login.isDisplayed()).as("login screen is displayed").isTrue();
    }

    @When("I log in with username {string} and password {string}")
    public void iLogInWith(String username, String password) {
        new LoginPage().loginAs(username, password);
    }

    @Then("I see the message {string}")
    public void iSeeTheMessage(String message) {
        assertThat(new LoginPage().isMessageDisplayed(message)).as("message '%s' is shown", message).isTrue();
    }

    @Then("I am taken to the catalog")
    public void iAmTakenToTheCatalog() {
        assertThat(new ProductsPage().isDisplayed()).as("catalog screen is displayed").isTrue();
    }
}
