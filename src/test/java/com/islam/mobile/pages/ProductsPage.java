package com.islam.mobile.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/** Catalog screen: product grid and sorting. */
public class ProductsPage extends BasePage {
    private final By screen = id("products screen");
    private final By itemNames = id("store item text");
    private final By itemPrices = id("store item price");
    private final By sortButton = id("sort button");

    public enum SortOption {
        NAME_ASC("nameAsc"), NAME_DESC("nameDesc"), PRICE_ASC("priceAsc"), PRICE_DESC("priceDesc");

        final String accessibilityId;

        SortOption(String accessibilityId) {
            this.accessibilityId = accessibilityId;
        }
    }

    @Override
    public boolean isDisplayed() {
        return isPresent(screen);
    }

    public List<String> visibleProductNames() {
        return allVisible(itemNames).stream().map(WebElement::getText).map(String::trim).toList();
    }

    public List<Double> visibleProductPrices() {
        return allVisible(itemPrices).stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "").trim()))
                .toList();
    }

    public void sortBy(SortOption option) {
        tap(sortButton);
        tap(id(option.accessibilityId));
    }

    public ProductDetailsPage openProduct(String name) {
        scrollToText(name).click();
        return new ProductDetailsPage();
    }
}
