package com.demowebshop.tests;

import org.junit.jupiter.api.*;
import com.demowebshop.test.base.BaseTest;

@DisplayName("Simple Test to get Youth Card")
public class SimpleTest extends BaseTest {

    @Test
    public void SimpleGetYouthCardTest() {
        apptest
                .getStartPage().getText();
    }
}