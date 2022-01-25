package com.example.inventoryapp.data;

public class Product {
    private String pName;
    private Integer pQuanity;
    private String pUnit;

    public Product(String pName, Integer pQuanity, String pUnit) {
        this.pName = pName;
        this.pQuanity = pQuanity;
        this.pUnit = pUnit;
    }

    public String getpName() {
        return pName;
    }

    public int getpQuanity() {
        return pQuanity;
    }

    public String getpUnit() {
        return pUnit;
    }
}
