package com.dws.test.base;

import com.dws.managers.InitManager;
import com.dws.managers.PageManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import static com.dws.helper.CartHelper.getCartHelper;

public class BaseTest {
    protected PageManager apptest = PageManager.getPageManager();
    
    @BeforeAll
    public static void beforeAll() {
        InitManager.initFramework();
    }

    @AfterAll
    public static void afterAll() {
        InitManager.quitFramework();
    }
    
    @BeforeEach
    public void beforeEach() {
        InitManager.openBrowser();
    }
    
    @AfterEach
    public void afterEach() {
        InitManager.clearCookies();
        getCartHelper().clearCartList();
    }
}
