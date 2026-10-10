package com.islam.mobile.steps;

import com.islam.mobile.context.ScenarioContext;
import com.islam.mobile.pages.AppHeader;
import com.islam.mobile.pages.CartPage;
import com.islam.mobile.pages.ProductDetailsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class CartSteps {
    private final ScenarioContext context;

    public CartSteps(ScenarioContext context) {
        this.context = context;
    }

    @When("I set the quantity to {int}")
    public void iSetTheQuantityTo(int quantity) {
        ProductDetailsPage details = new ProductDetailsPage();
        int current = details.quantity();
        if (quantity > current) details.increaseQuantity(quantity - current);
        if (quantity < current) details.decreaseQuantity(current - quantity);
        assertThat(details.quantity()).isEqualTo(quantity);
        context.put("quantity", quantity);
    }

    @When("I add the product to the cart")
    public void iAddTheProductToTheCart() {
        new ProductDetailsPage().addToCart();
    }

    @Then("the cart badge shows {int}")
    public void theCartBadgeShows(int count) {
        assertThat(new AppHeader().cartCount()).isEqualTo(count);
    }

    @When("I open the cart")
    public void iOpenTheCart() {
        assertThat(new AppHeader().openCart().isDisplayed()).as("cart screen is displayed").isTrue();
    }

    @Then("the cart contains the product")
    public void theCartContainsTheProduct() {
        String product = context.get("product");
        assertThat(new CartPage().containsProduct(product)).as("cart contains '%s'", product).isTrue();
    }

    @Then("the cart total matches quantity times unit price")
    public void theCartTotalMatches() {
        double unitPrice = context.get("unitPrice");
        int quantity = context.get("quantity");
        CartPage cart = new CartPage();
        assertThat(cart.totalItems()).isEqualTo(quantity);
        assertThat(cart.totalPrice()).isCloseTo(unitPrice * quantity, within(0.01));
        assertThat(cart.canCheckout()).as("checkout button is shown").isTrue();
    }

    @When("I remove the item from the cart")
    public void iRemoveTheItem() {
        new CartPage().removeFirstItem();
    }

    @Then("the cart is empty")
    public void theCartIsEmpty() {
        assertThat(new CartPage().isEmpty()).as("empty cart message is shown").isTrue();
        assertThat(new AppHeader().cartCount()).isZero();
    }
}
