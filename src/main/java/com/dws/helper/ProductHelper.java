package com.dws.helper;

import lombok.Getter;
import lombok.Setter;

public class ProductHelper {
    @Getter @Setter
    private String name;
    @Getter @Setter
    private float price;
    @Getter @Setter
    private int quantity;
    
    public ProductHelper(String name, float price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}