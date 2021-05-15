package com.dws.tests;

import org.junit.jupiter.api.*;
import com.dws.test.base.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Simple Test to get Youth Card")
public class ExampleTest extends BaseTest {
    String colorErrorMessage = "204, 0, 0";
    String textErrorMessage = "Quantity should be positive";
    String colorSuccessMessage = "145, 189, 9";
    String textSuccessMessage = "The product has been added to your shopping cart";

    @Test
    public void FirstTest() {
        // Go to the "Books" menu
        apptest
                .getMenuToPage()
                .clickLeftMenu("Books");
        // Open product page "Computing and Internet"
        apptest
                .getProductListPage()
                .clickToProduct("Computing and Internet");
        // Add a product to the cart and
        // check the correctness of the message
        apptest
                .getProductPage()
                .clickButtonAddToCart()
                .AssertBarNotificationColor(colorSuccessMessage)
                .AssertBarNotificationText(textSuccessMessage);
        // Go to the "Computers - Notebooks" menu
        apptest
                .getMenuToPage()
                .mouseMoveToTopMenu("Computers")
                .clickTopSubMenu("Notebooks");
        // Open product page "14.1-inch Laptop"
        apptest
                .getProductListPage()
                .clickToProduct("14.1-inch Laptop");
        // Add 2 items to cart and
        // check the correctness of the message
        apptest
                .getProductPage()
                .inputQuanityClear()
                .inputQuanityEnterText("2")
                .clickButtonAddToCart()
                .AssertBarNotificationColor(colorSuccessMessage);
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"-1", "f", "6.5", "4,3", "!", "%:;?", "0"})
    public void FailedTest(String input) {
        // Go to the "Books" menu
        apptest
                .getMenuToPage()
                .clickLeftMenu("Books");
        // Open product page "Computing and Internet"
        apptest
                .getProductListPage()
                .clickToProduct("Computing and Internet");
        // Add a product to the cart and
        // check the correctness of the message
        apptest
                .getProductPage()
                .inputQuanityClear()
                .inputQuanityEnterText(input)
                .clickButtonAddToCart()
                .AssertBarNotificationColor(colorErrorMessage)
                .AssertBarNotificationText(textErrorMessage);
    }
}