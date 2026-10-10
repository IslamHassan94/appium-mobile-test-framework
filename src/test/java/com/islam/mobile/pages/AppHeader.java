package com.islam.mobile.pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/** Header shared by every screen: side menu and cart badge. */
public class AppHeader extends BasePage {
    private final By openMenu = id("open menu");
    private final By cartBadge = id("cart badge");
    private final By cartBadgeCount = AppiumBy.xpath("//*[@content-desc='cart badge']/android.widget.TextView");

    @Override
    public boolean isDisplayed() {
        return isPresent(openMenu);
    }

    public MenuPage openMenu() {
        tap(openMenu);
        return new MenuPage();
    }

    public CartPage openCart() {
        tap(cartBadge);
        return new CartPage();
    }

    /** Number shown on the cart badge, 0 when the badge is empty. */
    public int cartCount() {
        List<WebElement> count = driver.findElements(cartBadgeCount);
        return count.isEmpty() ? 0 : Integer.parseInt(count.get(0).getText().trim());
    }
}
