package com.dws.tests;

import org.junit.jupiter.api.*;
import com.dws.test.base.BaseTest;

@DisplayName("Simple Test to get Youth Card")
public class SimpleTest extends BaseTest {

    @Test
    public void SimpleGetYouthCardTest() {
        apptest
                .getMenuToPage()
                .clickLeftMenu("Electronics")
                .clickLeftMenu("Cell phones");
        
        apptest
                .getProductListPage()
                .clickToProduct("Phone Cover");
        
        apptest
                .getMenuToPage()
                .clickLeftMenu("Electronics");
    }
}