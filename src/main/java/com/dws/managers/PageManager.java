package com.dws.managers;

import com.dws.pages.CartPage;
import com.dws.pages.ProductListPage;
import com.dws.pages.ProductPage;
import com.dws.pages.base.PageBase;
import com.dws.pages.MenuToPage;
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

    public MenuToPage getMenuToPage() {
        return getPage(MenuToPage.class);
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
            if(classPage == MenuToPage.class)
                mapPages.put(classPage.toString(), new MenuToPage(classPage.toString()));
            else if(classPage == ProductListPage.class)
                mapPages.put(classPage.toString(), new ProductListPage(classPage.toString()));
            else if(classPage == ProductPage.class)
                mapPages.put(classPage.toString(), new ProductPage(classPage.toString()));
            else if(classPage == CartPage.class)
                mapPages.put(classPage.toString(), new CartPage(classPage.toString()));
        }
        return (T) mapPages.get(classPage.toString());
    }

    void clearMapPage() {
        mapPages.clear();
    }
}
