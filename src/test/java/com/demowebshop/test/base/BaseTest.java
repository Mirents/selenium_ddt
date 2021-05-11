package com.demowebshop.test.base;

import com.demowebshop.managers.InitManager;
import com.demowebshop.managers.PageManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

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
}
