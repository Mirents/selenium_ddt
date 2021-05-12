package com.dws.managers;

import com.dws.base.PageBase;
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

    private <T extends PageBase> T getPage(Class<? extends PageBase> classPage) {
        if(mapPages.isEmpty() || mapPages.get(classPage.getClass().getName()) == null) {
            if(classPage == StartPage.class)
                mapPages.put(classPage.getClass().getName(), new StartPage("StartPage"));
        }
        
        return (T) mapPages.get(classPage.getClass().getName());
    }

    void clearMapPage() {
        mapPages.clear();
    }
}
