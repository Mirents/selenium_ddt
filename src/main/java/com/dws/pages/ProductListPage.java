package com.dws.pages;

import com.dws.pages.base.PageBase;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductListPage extends PageBase {

    @FindBy(xpath = "//div[@class='product-grid']//div[@class='product-item']")
    private List<WebElement> listProduct;
    
    public ProductListPage(String description) {
        super(description);
    }
    
    public ProductListPage clickToProduct(String name) {
        String xpath = "//h2//a[contains(text(),'" + name + "')]";
        getElemFromListToBy(listProduct, By.xpath(xpath)).click();
        return this;
    }
}
