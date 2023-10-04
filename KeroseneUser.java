package com.example.fuelq;

public class KeroseneUser {

    private String userId;
    private String name;
    private String email;
    private String userType;
    private String pw;
    private String address;
    private String fuelType;
    private int fuelQuota;
    private int availableQuota;
    private String id;

    public KeroseneUser(String userId, String name, String email, String userType, String pw, String address, String fuelType, int fuelQuota, int availableQuota,String id) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.userType = userType;
        this.pw = pw;
        this.address = address;
        this.fuelType = fuelType;
        this.fuelQuota = fuelQuota;
        this.availableQuota = availableQuota;
        this.id= id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public int getFuelQuota() {
        return fuelQuota;
    }

    public void setFuelQuota(int quota) {
        this.fuelQuota = fuelQuota;
    }

    public int getAvailableQuota() {
        return availableQuota;
    }

    public void setAvailableQuota(int availableQuota) {
        this.availableQuota = availableQuota;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
