package com.example.finalyearproject.Domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.UUID;

public class ProductDomain implements Serializable {

    private String title;
    private String pic;
    private String description;
    private double fee;
    private int numberInCart;

    private String sellerAddress;

    private String uniqueId ;

    private boolean status;

    private String userId;

    public ProductDomain() {

    }

    public ProductDomain(String title, String pic, String description, double fee,String sellerAddress,boolean status,String userId) {
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.fee = fee;
        this.sellerAddress = sellerAddress;
        this.status = status;
        this.userId = userId;
        this.uniqueId = UUID.randomUUID().toString();
    }

    public ProductDomain(String title, String pic, String description, double fee, int numberInCart, String sellerAddress, boolean status, String userId) {
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.fee = fee;
        this.numberInCart = numberInCart;
        this.sellerAddress = sellerAddress;
        this.uniqueId =  UUID.randomUUID().toString();
        this.status = status;
        this.userId = userId;
    }

    public ProductDomain(String title, String pic, String description, double fee, int numberInCart,String uniqueId, String sellerAddress, boolean status, String userId) {
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.fee = fee;
        this.numberInCart = numberInCart;
        this.sellerAddress = sellerAddress;
        this.uniqueId =  uniqueId;
        this.status = status;
        this.userId = userId;
    }

    public ProductDomain(String title, String pic, String description, double fee, int numberInCart) {
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.fee = fee;
        this.numberInCart = numberInCart;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }



    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
