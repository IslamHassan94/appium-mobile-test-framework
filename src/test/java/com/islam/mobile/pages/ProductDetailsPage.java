package com.islam.mobile.pages;

import org.openqa.selenium.By;

public class ProductDetailsPage extends BasePage {
    private final By screen = id("product screen");
    private final By price = id("product price");
    private final By plus = id("counter plus button");
    private final By minus = id("counter minus button");
    private final By amount = id("counter amount");
    private final By addToCart = id("Add To Cart button");

    @Override
    public boolean isDisplayed() {
        return isPresent(screen);
    }

    public boolean showsProduct(String name) {
        return isPresent(text(name));
    }

    public double price() {
        return Double.parseDouble(textOf(price).replace("$", "").trim());
    }

    public void increaseQuantity(int times) {
        for (int i = 0; i < times; i++) tap(plus);
    }

    public void decreaseQuantity(int times) {
        for (int i = 0; i < times; i++) tap(minus);
    }

    public int quantity() {
        return Integer.parseInt(textOf(amount));
    }

    public void addToCart() {
        tap(addToCart);
    }
}
