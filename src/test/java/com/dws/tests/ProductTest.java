package com.dws.tests;

import org.junit.jupiter.api.*;
import com.dws.test.base.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Testing the product page")
public class ProductTest extends BaseTest {
    String colorErrorMessage = "204, 0, 0";
    String textErrorMessage = "Quantity should be positive";
    String colorSuccessMessage = "145, 189, 9";
    String textSuccessMessage = "The product has been added to your shopping cart";

    @Test
    public void CheckCartPriceTest() {
        // Go to the "Books" menu
        apptest
                .getMenuToPage()
                .clickLeftMenu("Books");
        // Open product page "Computing and Internet"
        apptest
                .getProductListPage()
                .clickToProduct("Computing and Internet");
        // Add a product to the cart and
        apptest
                .getProductPage()
                .inputQuanityClear()
                .inputQuanityEnterNumber(3)
                .clickButtonAddToCart()
                .assertBarNotificationColor(colorSuccessMessage)
                .assertBarNotificationText(textSuccessMessage);
        // Go to the "Electronics - Cell phones" menu
        apptest
                .getMenuToPage()
                .clickLeftMenu("Electronics")
                .clickLeftMenu("Cell phones");
        // Open product page "Cell phones"
        apptest
                .getProductListPage()
                .clickToProduct("Phone Cover");
        // Add a product to the cart and
        apptest
                .getProductPage()
                .inputQuanityClear()
                .inputQuanityEnterNumber(4)
                .clickButtonAddToCart()
                .assertBarNotificationColor(colorSuccessMessage)
                .assertBarNotificationText(textSuccessMessage);
        // Checking product quantity in the top menu
        apptest
                .getMenuToPage()
                .assertLabelShoppingCartQuantity()
                .goToCart()
                .assertTotalPrice();
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"5", "94", "1025"})
    public void SuccessMessageTest(int input) {
        // Go to the "Electronics - Camera, photo" menu
        apptest
                .getMenuToPage()
                .mouseMoveToTopMenu("Computers")
                .clickTopSubMenu("Notebooks");
        // Open product page "Computing and Internet"
        apptest
                .getProductListPage()
                .clickToProduct("14.1-inch Laptop");
        // Add a product to the cart and
        // check the correctness of the message
        apptest
                .getProductPage()
                .inputQuanityClear()
                .inputQuanityEnterNumber(input)
                .clickButtonAddToCart()
                .assertBarNotificationColor(colorSuccessMessage)
                .assertBarNotificationText(textSuccessMessage);
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"-1", "f", "6.5", "4,3", "!", "%:;?", "0"})
    public void ErrorMessageTest(String input) {
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
                .assertBarNotificationColor(colorErrorMessage)
                .assertBarNotificationText(textErrorMessage);
    }
    
    @Test
    public void CheckBrokenImageHomePageTest() {
        // Find Broken Image on Home Page
        apptest
                .getMenuToPage()
                .findBrokenImage();
    }
    
    @Test
    public void CheckBrokenImageProductListPageTest() {
        // Find broken image on product listing page
        apptest
                .getMenuToPage()
                .clickLeftMenu("Jewelry")
                .findBrokenImage();
        
        // Find broken image on product page
        apptest
                .getMenuToPage()
                .clickLeftMenu("Jewelry");
        apptest
                .getProductListPage()
                .clickToProduct("Create Your Own Jewelry");
        apptest
                .getProductPage()
                .findBrokenImage();
    }
    
    @Test
    public void CheckBrokenImageProductPageTest() {
        // Find broken image on product page
        apptest
                .getMenuToPage()
                .clickLeftMenu("Jewelry");
        apptest
                .getProductListPage()
                .clickToProduct("Create Your Own Jewelry");
        apptest
                .getProductPage()
                .findBrokenImage();
    }
}