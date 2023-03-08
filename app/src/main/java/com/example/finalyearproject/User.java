package com.example.finalyearproject;

public class User {

    String name;
    String email;
    String address;
    String ethAddress;
    String uniqueId;

    public User(String n,String e,String address,String ethAddress,String uniqueId){
        this.name = n;
        this.email = e;
        this.address = address;
        this.ethAddress = ethAddress;
        this.uniqueId = uniqueId;
    }

    public User(){
        this.name = null;
        this.email = null;
        this.address = null;
        this.ethAddress = null;
        this.uniqueId = null;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEthAddress() {
        return ethAddress;
    }

    public void setEthAddress(String ethAddress) {
        this.ethAddress = ethAddress;
    }
}
