package com.islam.mobile.steps;

import com.islam.mobile.context.ScenarioContext;
import com.islam.mobile.pages.ProductDetailsPage;
import com.islam.mobile.pages.ProductsPage;
import com.islam.mobile.pages.ProductsPage.SortOption;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CatalogSteps {
    private final ScenarioContext context;

    public CatalogSteps(ScenarioContext context) {
        this.context = context;
    }

    @When("I sort the products by {string}")
    public void iSortTheProductsBy(String option) {
        new ProductsPage().sortBy(SortOption.valueOf(option));
    }

    @Then("the product names are sorted {string}")
    public void theNamesAreSorted(String direction) {
        List<String> names = new ProductsPage().visibleProductNames();
        Comparator<String> order = String.CASE_INSENSITIVE_ORDER;
        assertThat(names).isSortedAccordingTo("descending".equals(direction) ? order.reversed() : order);
    }

    @Then("the product prices are sorted {string}")
    public void thePricesAreSorted(String direction) {
        List<Double> prices = new ProductsPage().visibleProductPrices();
        Comparator<Double> order = Comparator.naturalOrder();
        assertThat(prices).isSortedAccordingTo("descending".equals(direction) ? order.reversed() : order);
    }

    @When("I open the product {string}")
    public void iOpenTheProduct(String name) {
        ProductDetailsPage details = new ProductsPage().openProduct(name);
        assertThat(details.isDisplayed()).as("product screen is displayed").isTrue();
        context.put("product", name);
        context.put("unitPrice", details.price());
    }

    @Then("the product details show {string} for {double}")
    public void theDetailsShow(String name, double price) {
        ProductDetailsPage details = new ProductDetailsPage();
        assertThat(details.showsProduct(name)).as("product name '%s' is shown", name).isTrue();
        assertThat(details.price()).isEqualTo(price);
    }
}
