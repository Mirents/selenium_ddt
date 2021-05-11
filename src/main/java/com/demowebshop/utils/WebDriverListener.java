package com.demowebshop.utils;

import com.demowebshop.managers.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDriverListener implements WebDriverEventListener {
    private final String color;
    private final int interval;
    private final int count;
    
    public WebDriverListener(String color, int interval, int count) {
        this.color = color;
        this.interval = interval;
        this.count = count;
    }
    
    private static final Logger LOGGER = LoggerFactory
            .getLogger(WebDriverListener.class);

    @Override
    public void beforeAlertAccept(WebDriver wd) {
        LOGGER.trace("beforeAlertAccept");
    }

    @Override
    public void afterAlertAccept(WebDriver wd) {
        LOGGER.trace("afterAlertAccept");
    }

    @Override
    public void afterAlertDismiss(WebDriver wd) {
        LOGGER.trace("afterAlertDismiss");
    }

    @Override
    public void beforeAlertDismiss(WebDriver wd) {
        LOGGER.trace("beforeAlertDismiss");
    }

    @Override
    public void beforeNavigateTo(String string, WebDriver wd) {
        LOGGER.trace("beforeNavigateTo");
    }

    @Override
    public void afterNavigateTo(String string, WebDriver wd) {
        LOGGER.trace("afterNavigateTo");
    }

    @Override
    public void beforeNavigateBack(WebDriver wd) {
        LOGGER.trace("beforeNavigateBack");
    }

    @Override
    public void afterNavigateBack(WebDriver wd) {
        LOGGER.trace("afterNavigateBack");
    }

    @Override
    public void beforeNavigateForward(WebDriver wd) {
        LOGGER.trace("beforeNavigateForward");
    }

    @Override
    public void afterNavigateForward(WebDriver wd) {
        LOGGER.trace("afterNavigateForward");
    }

    @Override
    public void beforeNavigateRefresh(WebDriver wd) {
        LOGGER.trace("beforeNavigateRefresh");
    }

    @Override
    public void afterNavigateRefresh(WebDriver wd) {
        LOGGER.trace("afterNavigateRefresh");
    }

    @Override
    public void beforeFindBy(By by, WebElement we, WebDriver wd) {
        LOGGER.trace("beforeFindBy");
    }

    @Override
    public void afterFindBy(By by, WebElement we, WebDriver wd) {
        LOGGER.trace("afterFindBy");
    }

    @Override
    public void beforeClickOn(WebElement we, WebDriver wd) {
        LOGGER.trace("beforeClickOn");        
        flash(we);
    }

    @Override
    public void afterClickOn(WebElement we, WebDriver wd) {
        LOGGER.trace("afterClickOn");
    }

    @Override
    public void beforeChangeValueOf(WebElement we, WebDriver wd, CharSequence[] css) {
        LOGGER.trace("beforeChangeValueOf");
        flash(we);
    }

    @Override
    public void afterChangeValueOf(WebElement we, WebDriver wd, CharSequence[] css) {
        LOGGER.trace("afterChangeValueOf");
    }

    @Override
    public void beforeScript(String string, WebDriver wd) {
        LOGGER.trace("beforeScript");
    }

    @Override
    public void afterScript(String string, WebDriver wd) {
        LOGGER.trace("afterScript");
    }

    @Override
    public void beforeSwitchToWindow(String string, WebDriver wd) {
        LOGGER.trace("beforeSwitchToWindow");
    }

    @Override
    public void afterSwitchToWindow(String string, WebDriver wd) {
        LOGGER.trace("afterSwitchToWindow");
    }

    @Override
    public void onException(Throwable thrwbl, WebDriver wd) {
        LOGGER.trace("onException");
    }

    @Override
    public <X> void beforeGetScreenshotAs(OutputType<X> ot) {
        LOGGER.trace("beforeGetScreenshotAs");
    }

    @Override
    public <X> void afterGetScreenshotAs(OutputType<X> ot, X x) {
        LOGGER.trace("afterGetScreenshotAs");
    }

    @Override
    public void beforeGetText(WebElement we, WebDriver wd) {
        LOGGER.trace("beforeGetText");
    }

    @Override
    public void afterGetText(WebElement we, WebDriver wd, String string) {
        LOGGER.trace("afterGetText");
    }
    
    private void flash(WebElement element) {
        JavascriptExecutor js = ((JavascriptExecutor) DriverManager.getDriver());
        String bgcolor  = element.getCssValue("backgroundColor");
        
        for (int i = 0; i < count; i++) {
            changeColor(color, element, js);
            changeColor(bgcolor, element, js);
        }
    }

    private void changeColor(String color, WebElement element,  JavascriptExecutor js) {
        js.executeScript("arguments[0].style.backgroundColor = '"+color+"'",  element);
        try {
            Thread.sleep(interval);
        }  catch (InterruptedException ignore) {}
    }
}
