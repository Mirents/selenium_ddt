package com.dws.pages.base;

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
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageBase {
    protected static final Logger LOGGER = LoggerFactory.getLogger(PageBase.class);
    protected WebDriverWait wait = new WebDriverWait(getDriver(), 3, 1000);
    protected Actions action = new Actions(getDriver());
    private final String description;
    private String windowHandle = "";
    
    public PageBase(String description) {
        this.description = description;
        PageFactory.initElements(getDriver(), this);
    }    
    
    public String getDescription() {
        return description;
    }
    
    public String getDescriptionToDot() {
        return description + ".";
    }
    
    public void findBrokenLinks() {
        List<WebElement> links = getDriver().findElements(By.tagName("a"));
 
        if(links.size() > 0) {
            for(int i=0;i<links.size();i++)
            {
                WebElement element = links.get(i);
                String url = element.getAttribute("href");
                isBrokenLink(url);
            }
        }
    }

    public void findBrokenImage() {
        List<WebElement> images = getDriver().findElements(By.tagName("img"));

        if(images.size() > 0) {
            for(int index=0;index<images.size();index++)
            {
                WebElement image = images.get(index);
                String imageURL= image.getAttribute("src");
                
                isBrokenLink(imageURL);

                try {
                    boolean imageDisplayed = execScript("return (typeof arguments[0].naturalWidth !=\"undefined\" && arguments[0].naturalWidth > 0);", image);
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
    
    public String getWindowHandle() {
        return windowHandle;
    }
    
    public void setWindowHandle(String windowHandle) {
        this.windowHandle = windowHandle;
    }
    
    public void endTests() {
        LOGGER.debug("Finishing the page {}", getDescription());
    }
}
