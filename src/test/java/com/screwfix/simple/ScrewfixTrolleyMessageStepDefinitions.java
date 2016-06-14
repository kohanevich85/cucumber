package com.screwfix.simple;

import com.codeborne.selenide.WebDriverRunner;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.google.common.base.Strings.isNullOrEmpty;

public class ScrewfixTrolleyMessageStepDefinitions {

    String productId;
    String productQuantity;
    String message;

    @Given("an open browser with home page")
    public void openHomePage() {
        open("http://epuakyiw0954.kyiv.epam.com:7551/jsp/container.jsp");
    }

    @Given("setup branch")
    public void setupBranch() {
        Cookie ck = new Cookie("selectedBranch", "YS1");
        WebDriverRunner.getWebDriver().manage().addCookie(ck);
    }

    @When("click quick shop link")
    public void chooseImagesAsSearchTarget() {
        $("#quickshop_page_link").click();
    }

    @When("enter a productId (.*) in input field")
    public void enterProductId(String productId) {
        this.productId = productId;
            $("#product-code_1").val(productId);
    }

    @When("enter a productQuantity (.*) in input field")
    public void enterProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
        $("#trolley_page_product_quantity_1").val(productQuantity).pressEnter();
    }

    @Then("Delivery message on (\\d+) position of product should be '(.+)'$")
    public void checkDeliveryMessage(int position, String expectedMessage) {
        this.message = expectedMessage;
        String actualMessage = $("#trolley_page_product_delivery_info_" + position).getText();
        Assert.assertTrue(expectedMessage.equals(actualMessage));
    }

    @Then("Collection message on (\\d+) position of product should be '(.+)'$")
    public void checkCollectionMessage(int position, String expectedMessage) {
        this.message = expectedMessage;
        String actualMessage = $("#trolley_page_product_collection_info_" + position).getText();
        Assert.assertTrue(expectedMessage.equals(actualMessage));
    }

    @When("Move to CPC if needed (true|false) with (.*)")
    public void moveToCpc(Boolean condition, String productId) {
        if (condition) {
            $("#trolley_page_move_item_to_collection_" + productId).click();
        }
    }

    @When("Update quantity to (.*) on (.*) position with (.*)")
    public void updateQuantity(String qty, String position, String prefix) {
        $("#" + prefix + "trolley_page_product_quantity_" + position).val(qty).pressEnter();
    }


    @Then("Check on (.*) with (.*) error message should be '(.+)'$")
    public void checkErrorMessage(String productId, String prefix, String expectedMessage) {
        if (isNullOrEmpty(expectedMessage)) {
            String actualMessage = $("#" + prefix + "trolley_page_product_error_" + productId).getText();
            Assert.assertTrue(expectedMessage.equals(actualMessage));
        }
    }

    @When("Go to checkout page if needed (true|false)")
    public void gotoCheckOutPage(Boolean shouldComeBack) {
        $("#topCheckoutButton").click();
        if (shouldComeBack) {
            WebDriverRunner.getWebDriver().navigate().to("http://epuakyiw0954.kyiv.epam.com:7551/jsp/trolley/trolleyPage.jsp");
        }
    }

    @Then("Remove item from trolley on (\\d+) position")
    public void removeItemFromTrolley(String position) {
        $("#trolley_page_product_remove_item_link_" + position).click();
    }
}
