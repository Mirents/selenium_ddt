package com.dws.tests;

import org.junit.jupiter.api.*;
import com.dws.test.base.BaseTest;

@DisplayName("Simple Test to get Youth Card")
public class SimpleTest extends BaseTest {

    @Test
    public void SimpleGetYouthCardTest() {
        apptest
                .getStartPage()
                .mouseMoveToTopMenu("Computers")
                .clickTopMenu("Desktops")
                .mouseMoveToTopMenu("Jewelry")
                .mouseMoveToTopMenu("Electronics")
                .clickTopMenu("Cell phones")
                .mouseMoveToTopMenu("Computers")
                .clickTopMenu("Accessories")
                .clickLeftMenu("Jewelry")
                .clickLeftMenu("Books")
                .clickLeftMenu("Electronics")
                .clickLeftMenu("Cell phones")
                .endTests();
    }
}