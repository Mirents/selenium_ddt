package com.dws.pages.base;

import com.dws.helper.CartHelper;
import static com.dws.managers.DriverManager.getDriver;
import static com.dws.managers.PropertiesManager.getThisProperties;
import static com.dws.utils.ProperitesConstant.DRIVER_IMPLICITY_WAIT;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageBase {
    protected static final Logger LOGGER = LoggerFactory.getLogger(PageBase.class);
    protected WebDriverWait wait = new WebDriverWait(getDriver(), 3, 1000);
    protected Actions action = new Actions(getDriver());
    protected CartHelper cartHelper;
    private final String description;
    
    public PageBase(String description) {
        this.description = description;
        PageFactory.initElements(getDriver(), this);
    }    
    
    public String getDescription() {
        return description;
    }
    
    public <T extends PageBase> T findBrokenLinks() {
        List<WebElement> links = getDriver().findElements(By.tagName("a"));
 
        if(links.size() > 0) {
            for(int i=0;i<links.size();i++)
            {
                WebElement element = links.get(i);
                String url = element.getAttribute("href");
                isBrokenLink(url);
            }
        }
        return (T) this;
    }

    public <T extends PageBase> T findBrokenImage() {
        List<WebElement> images = getDriver().findElements(By.tagName("img"));

        if(images.size() > 0) {
            for(int index=0;index<images.size();index++)
            {
                WebElement image = images.get(index);
                String imageURL= image.getAttribute("src");
                
                isBrokenLink(imageURL);

                try {
                    boolean imageDisplayed =
                            execScript("return (typeof arguments[0]"
                                    + ".naturalWidth !=\"undefined\" "
                                    + "&& arguments[0].naturalWidth > 0);"
                                    , image);
                    if(!imageDisplayed) {
                        Assertions.fail("On the screen - a broken image with a link - "
                        + imageURL);
                    }
                } catch (Exception ex) {
                    Assertions.fail("Image verification error by reference - "
                    + imageURL);
                }
            }
        }
        return (T) this;
    }

    private void isBrokenLink(String linkUrl) {
        try {
            URL url = new URL(linkUrl);
 
            HttpURLConnection httpURLConnect=(HttpURLConnection)url.openConnection();
            httpURLConnect.setConnectTimeout(5000);
            httpURLConnect.connect();
            if(httpURLConnect.getResponseCode()>=400) {
                Assertions.fail("Broken link - " + linkUrl + " - "
                        + httpURLConnect.getResponseMessage());
            }
        } catch (IOException ignore) {}
    }
    
    private boolean execScript(String script, WebElement element) {
        return (Boolean) ((JavascriptExecutor) getDriver())
                        .executeScript(script, element);
    }
    
    public boolean isElementExist(By by) {
        boolean flag = false;
        try {
            getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            getDriver().findElement(by);
            flag = true;
        } catch (NoSuchElementException ignore) {}
        finally {
            getDriver().manage().timeouts().implicitlyWait(
                    Integer.parseInt(getThisProperties()
                            .getProperty(DRIVER_IMPLICITY_WAIT)), TimeUnit.SECONDS);
        }
        return flag;
    }
    
    protected void clickToElementFromList(List<WebElement> list, String name) {
        LOGGER.debug(">> clickToElementFromList {}", name);
        WebElement element = getElemFromListToName(list, name);
        element.click();
        LOGGER.debug("<< clickToElementFromList {}", name);
    }
    
    protected void mouseMoveToElementFromList(List<WebElement> list, String name) {
        LOGGER.debug(">> mouseMoveToElementFromList {}", name);
        WebElement element = getElemFromListToName(list, name);
        action.moveToElement(element).build().perform();
        LOGGER.debug("<< mouseMoveToElementFromList {}", name);
    }

    protected WebElement getElemFromListToName(List<WebElement> list, String name) {
        LOGGER.debug(">> getElemFromListByName {}", name);
        for (WebElement element: list) {
            if (element.getText().equalsIgnoreCase(name)) {
                wait.until(ExpectedConditions.visibilityOf(element));
                LOGGER.debug("<< getElemFromListByName {}", name);
                return element;
            }
        }
        String msg = "Element to name '" + name + "' not found to "
                + this.getClass().getName();
        LOGGER.error(msg);
        Assertions.fail(msg);
        return null;
    }
    
    public WebElement getElemFromListToBy(List<WebElement> list, By by) {
        LOGGER.debug(">> getElemFromListToBy {}", by);
        for (WebElement element: list) {
            wait.until(ExpectedConditions.visibilityOf(element));
            WebElement findElement = element.findElement(by);
            LOGGER.debug("<< getElemFromListToBy {}", by);
            return findElement;
        }
        String msg = "Element to by '" + by + "' not found to "
                + this.getClass().getName();
        LOGGER.error(msg);
        Assertions.fail(msg);
        return null;
    }
}
