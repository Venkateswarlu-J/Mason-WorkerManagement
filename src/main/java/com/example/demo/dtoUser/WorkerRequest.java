package com.example.demo.dtoUser;

import com.example.demo.enums.Categories;

public class WorkerRequest {
    private String email;
    private String workerName;
    private String workerPhone;
    private Categories workerCat;
    private String workerAddress;
    private int payPerDay;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerPhone() {
        return workerPhone;
    }

    public void setWorkerPhone(String workerPhone) {
        this.workerPhone = workerPhone;
    }

    public Categories getWorkerCat() {
        return workerCat;
    }

    public void setWorkerCat(Categories workerCat) {
        this.workerCat = workerCat;
    }

    public String getWorkerAddress() {
        return workerAddress;
    }

    public void setWorkerAddress(String workerAddress) {
        this.workerAddress = workerAddress;
    }

    public int getPayPerDay() {
        return payPerDay;
    }

    public void setPayPerDay(int payPerDay) {
        this.payPerDay = payPerDay;
    }

    @Override
    public String toString() {
        return "WorkerRequest{" +
                "email='" + email + '\'' +
                ", workerName='" + workerName + '\'' +
                ", workerPhone='" + workerPhone + '\'' +
                ", workerCat='" + workerCat + '\'' +
                ", workerAddress='" + workerAddress + '\'' +
                ", payPerDay=" + payPerDay +
                '}';
    }
}
