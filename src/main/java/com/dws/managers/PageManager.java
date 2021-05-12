package com.dws.managers;

import com.dws.pages.CartPage;
import com.dws.pages.ProductListPage;
import com.dws.pages.ProductPage;
import com.dws.pages.base.PageBase;
import com.dws.pages.StartPage;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(PageManager.class);
    private static PageManager INSTANCE;
    private static Map<String, PageBase> mapPages = new HashMap<>();

    public static PageManager getPageManager() {
        if(INSTANCE == null) {
            INSTANCE = new PageManager();
        }
        return INSTANCE;
    }

    public StartPage getStartPage() {
        return getPage(StartPage.class);
    }
    
    public ProductListPage getProductListPage() {
        return getPage(ProductListPage.class);
    }
    
    public ProductPage getProductPage() {
        return getPage(ProductPage.class);
    }
    
    public CartPage getCartPage() {
        return getPage(CartPage.class);
    }

    private <T extends PageBase> T getPage(Class<? extends PageBase> classPage) {
        if(mapPages.isEmpty() || mapPages.get(classPage.getClass().getName()) == null) {
            if(classPage == StartPage.class)
                mapPages.put(classPage.getClass().getName(), new StartPage("StartPage"));
            else if(classPage == ProductListPage.class)
                mapPages.put(classPage.getClass().getName(), new ProductListPage("ProductListPage"));
            else if(classPage == ProductPage.class)
                mapPages.put(classPage.getClass().getName(), new ProductPage("ProductPage"));
            else if(classPage == CartPage.class)
                mapPages.put(classPage.getClass().getName(), new CartPage("CartPage"));
        }
        
        return (T) mapPages.get(classPage.getClass().getName());
    }

    void clearMapPage() {
        mapPages.clear();
    }
}
