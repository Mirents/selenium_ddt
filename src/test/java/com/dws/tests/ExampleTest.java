package com.dws.tests;

import static com.dws.helper.CartHelper.geCartHelper;
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
        // Checking product quantity in the top menu
        apptest
                .getMenuToPage()
                .assertLabelShoppingCartQuantity()
                .goToCart()
                .assertTotalPrice();
    }
    
    @Disabled("Временно отключен")
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
                .assertBarNotificationColor(colorErrorMessage)
                .assertBarNotificationText(textErrorMessage);
    }
}