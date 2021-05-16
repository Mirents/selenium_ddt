package com.dws.helper;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CartHelper {
    protected static final Logger LOGGER = LoggerFactory.getLogger(CartHelper.class);
    private static CartHelper INSTANCE;
    private List<ProductHelper> cart;
    
    public static CartHelper getCartHelper() {
        if(INSTANCE == null) {
            INSTANCE = new CartHelper();
        }
        return INSTANCE;
    }

    private CartHelper() {
        this.cart = new ArrayList<>();
    }
    
    public void addProduct(ProductHelper product) {
        cart.add(product);
    }
    
    public boolean isExistsProductByName(String name) {
        for(ProductHelper ph: cart)
            if(ph.getName().equals(name))
                return true;
        return false;
    }
    
    public ProductHelper getProductByName(String name) {
        for(ProductHelper ph: cart)
            if(ph.getName().equals(name))
                return ph;
        return null;
    }
    
    public float getTotalPrice() {
        float total = 0.0f;
        for(ProductHelper ph: cart)
            total += (ph.getPrice() * ph.getQuantity());
        return total;
    }
    
    public int getTotalQuantity() {
        int total = 0;
        for(ProductHelper ph: cart)
            total += ph.getQuantity();
        return total;
    }
    
    public void clearCartList() {
        cart.clear();
    }
    
    @Override
    public String toString() {
        String data = "";
        for(ProductHelper ph: cart)
            data += ph.getName() + " - " + ph.getPrice() + " - "
                    + ph.getQuantity();
        LOGGER.debug(data);
        return data;
    }
}
