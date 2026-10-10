package com.islam.mobile.pages;

import org.openqa.selenium.By;

public class MenuPage extends BasePage {
    private final By catalog = id("menu item catalog");
    private final By login = id("menu item log in");

    @Override
    public boolean isDisplayed() {
        return isPresent(catalog);
    }

    public LoginPage goToLogin() {
        tap(login);
        return new LoginPage();
    }

    public ProductsPage goToCatalog() {
        tap(catalog);
        return new ProductsPage();
    }
}
