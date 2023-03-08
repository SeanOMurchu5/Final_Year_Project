package com.example.finalyearproject;

import java.io.Serializable;

public class Product implements Serializable {

    private String productName;
    private double productPrice;
    private String description;
    private String sellerAddress;
    private boolean status;

    public Product(){

    }

    public Product(String pn, double pp, String desc, String SA, boolean s){
        this.productName = pn;
        this.productPrice = pp;
        this.description = desc;
        this.sellerAddress = SA;
        this.status = s;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
