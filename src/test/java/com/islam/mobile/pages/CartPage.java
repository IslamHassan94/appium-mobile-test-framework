package com.islam.mobile.pages;

import org.openqa.selenium.By;

public class CartPage extends BasePage {
    private final By screen = id("cart screen");
    private final By productRows = id("product row");
    private final By removeItem = id("remove item");
    private final By totalNumber = id("total number");
    private final By totalPrice = id("total price");
    private final By checkout = id("Proceed To Checkout button");

    @Override
    public boolean isDisplayed() {
        return isPresent(screen);
    }

    public int numberOfRows() {
        return driver.findElements(productRows).size();
    }

    public boolean containsProduct(String name) {
        return isPresent(text(name));
    }

    /** e.g. "2 items" becomes 2 */
    public int totalItems() {
        return Integer.parseInt(textOf(totalNumber).replaceAll("\\D+", ""));
    }

    public double totalPrice() {
        return Double.parseDouble(textOf(totalPrice).replace("$", "").trim());
    }

    public void removeFirstItem() {
        tap(removeItem);
    }

    public boolean isEmpty() {
        return isPresent(text("No Items"));
    }

    public boolean canCheckout() {
        return isPresent(checkout);
    }
}
