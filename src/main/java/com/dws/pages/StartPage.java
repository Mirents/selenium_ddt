package com.dws.pages;

import com.dws.pages.base.PageBase;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class StartPage extends PageBase {
    @FindBy(xpath = "//ul[@class='top-menu']//li")
    private List<WebElement> listTopMenu;
    
    @FindBy(xpath = "//ul[@class='top-menu']//a")
    private List<WebElement> listTopSubMenu;
    
    @FindBy(xpath = "//div[contains(@class,'block-category')]//a")
    private List<WebElement> listLeftMenuCategories;
    
    public StartPage(String description) {
        super(description);
    }

    public StartPage mouseMoveToTopMenu(String nameTopMenu) {
        mouseMoveOrClickMenu(listTopMenu, nameTopMenu, false);
        return this;
    }
    
    public StartPage clickTopMenu(String nameTopMenu) {        
        mouseMoveOrClickMenu(listTopMenu, nameTopMenu, true);
        return this;
    }
    
    public StartPage clickTopSubMenu(String nameTopSubMenu) {
        mouseMoveOrClickMenu(listTopSubMenu, nameTopSubMenu, true);
        return this;
    }
    
    public StartPage clickLeftMenu(String nameTopMenu) {        
        mouseMoveOrClickMenu(listLeftMenuCategories, nameTopMenu, true);
        return this;
    }
    
    private boolean mouseMoveOrClickMenu(List<WebElement> list, String nameMenu,
            boolean onClick) {
        LOGGER.debug(">> selectOrClickMenu {} - {}", nameMenu, onClick);
        for (WebElement menuItem : list) {
            if (menuItem.getText().equalsIgnoreCase(nameMenu)) {
                wait.until(ExpectedConditions.visibilityOf(menuItem));
                if(onClick)
                    menuItem.click();
                else
                    action.moveToElement(menuItem).build().perform();
                LOGGER.debug("<< selectOrClickMenu {}", nameMenu);
                return true;
            }
        }
        String msg = "Menu '" + nameMenu + "' not found to StartPage!";
        LOGGER.error(msg);
        Assertions.fail(msg);
        
        return false;
    }
}