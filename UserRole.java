package com.example.fuelq;

import java.io.Serializable;

public class UserRole implements Serializable {

    private String userId;
    private String nic;
    private String name;
    private String email;
    private String userType;
    private String pw;
    private String address;
    private String fuelType;
    private int fuelQuota;
    private int availableQuota;
    private String chassisNo;
    private String vehicleType;
    private String id;


    public UserRole(String userId, String nic, String name, String email, String userType,
                    String address, int fuelQuota, String chassisNo, String pw, String fuelType, int availableQuota, String vehicleType, String id) {
        this.userId = userId;
        this.nic = nic;
        this.name = name;
        this.email = email;
        this.userType = userType;
        this.address = address;
        this.fuelQuota = fuelQuota;
        this.chassisNo = chassisNo;
        this.fuelType = fuelType;
        this.pw = pw;
        this.availableQuota = availableQuota;
        this.vehicleType = vehicleType;
        this.id = id;

    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getChassisNo() {
        return chassisNo;
    }

    public void setChassisNo(String chassisNo) {
        this.chassisNo = chassisNo;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelQuota(){ this.fuelQuota = fuelQuota; }

    public int getFuelQuota(){ return fuelQuota; }

    public void setAvailableQuota(){ this.availableQuota = availableQuota;}

    public int getAvailableQuota(){ return availableQuota;}

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
