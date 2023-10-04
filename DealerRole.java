package com.example.fuelq;

public class DealerRole {

    private String userId;
    private String name;
    private String email;
    private String userType;
    private String pw;
    private String address;
    private String id;
    private String petrol92;
    private String petrol95;
    private String diesel;
    private String dieselSuper;
    private String kerosene;



    public DealerRole(String userId,String name, String email, String userType, String pw, String address, String id, String petrol92, String petrol95, String diesel,String dieselSuper, String kerosene) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.userType = userType;
        this.pw = pw;
        this.address = address;
        this.id = id;
        this.petrol92 = petrol92;
        this.petrol95= petrol95;
        this.diesel = diesel;
        this.dieselSuper = dieselSuper;
        this.kerosene = kerosene;
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

    public String getId() {
        return id;
    }

    public void setId(String dealerRegNo) {
        this.id = id;
    }

    public String getPetrol92() {
        return petrol92;
    }

    public void setPetrolCapacity(String petrol92) {
        this.petrol92 = petrol92;
    }

    public String getDiesel() {
        return diesel;
    }

    public void setDiesel(String diesel) {
        this.diesel= diesel;
    }

    public String getPetrol95() {
        return petrol95;
    }

    public void setPetrol95(String petrol95) {
        this.petrol95 = petrol95;
    }

    public String getDieselSuper() {
        return dieselSuper;
    }

    public void setDieselSuper(String dieselSuper) {
        this.dieselSuper = dieselSuper;
    }

    public String getKerosene() {
        return kerosene;
    }

    public void setKeroseneCapacity(String kerosene) {
        this.kerosene = kerosene;
    }
}
