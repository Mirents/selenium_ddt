package com.dws.pages;

import static com.dws.helper.CartHelper.geCartHelper;
import com.dws.pages.base.PageBase;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends PageBase {
    
    @FindBy(xpath = "//td[@class='cart-total-right']//strong")
    private WebElement labelTotal;
    
    public CartPage(String description) {
        super(description);
    }
    
    public CartPage assertTotalPrice() {
        float fromLabelTotal = Float.parseFloat(labelTotal.getText());
        Assertions.assertEquals(geCartHelper().getTotalPrice(), fromLabelTotal,
                "Checking the total value of the goods");
        return this;
    }
}
