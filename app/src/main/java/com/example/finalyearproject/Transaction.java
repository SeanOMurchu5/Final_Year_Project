package com.example.finalyearproject;

import java.io.Serializable;

public class Transaction implements Serializable {

    public String senderAddress;
    public String receiverAddress;
    public Double amount;
    public String uniqueId;
    public String status;

    public Transaction(){

    }

    public Transaction(String sa, String ra, double a, String uID,String stat){
        senderAddress  = sa;
        receiverAddress = ra;
        amount = a;
        uniqueId = uID;
        status = stat;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
