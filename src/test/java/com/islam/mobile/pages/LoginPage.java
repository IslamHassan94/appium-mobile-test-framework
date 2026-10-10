package com.islam.mobile.pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    private final By screen = id("login screen");
    private final By username = id("Username input field");
    private final By password = id("Password input field");
    private final By loginButton = id("Login button");

    @Override
    public boolean isDisplayed() {
        return isPresent(screen);
    }

    public void loginAs(String user, String pass) {
        type(username, user);
        type(password, pass);
        tap(loginButton);
    }

    public boolean isMessageDisplayed(String message) {
        return isPresent(text(message));
    }
}
