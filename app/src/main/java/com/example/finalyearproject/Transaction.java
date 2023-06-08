package com.example.finalyearproject;

import com.example.finalyearproject.Domain.ProductDomain;

import java.io.Serializable;
import java.util.UUID;

public class Transaction implements Serializable {

    public String senderAddress;
    public String receiverAddress;
    public Double amount;
    public ProductDomain product;
    public String uniqueId;
    public boolean status;

    public Transaction(){
        uniqueId = UUID.randomUUID().toString();;

    }

    public Transaction(String sa, String ra, double a, String uID,boolean stat){
        senderAddress  = sa;
        receiverAddress = ra;
        amount = a;
        uniqueId = uID;
        status = stat;
    }
    public Transaction(String sa, String ra, double a,boolean stat){
        senderAddress  = sa;
        receiverAddress = ra;
        amount = a;
        uniqueId = UUID.randomUUID().toString();;
        status = stat;
    }

    public ProductDomain getProduct() {
        return product;
    }

    public void setProduct(ProductDomain product) {
        this.product = product;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }


}
