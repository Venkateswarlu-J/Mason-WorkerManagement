package com.example.demo.dtoUser;


import com.example.demo.enums.Categories;
import com.example.demo.enums.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class PaymentWorkerDetailsDTO {

    private String workerName;
    private Categories categories;
    private double paymentPerDay;
    private double fullDays;
    private double halfDays;
    private double totalDays;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private double totalAmount;
    private double paid;
    private double toBePaid;

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
    }

    public double getToBePaid() {
        return toBePaid;
    }

    public void setToBePaid(double toBePaid) {
        this.toBePaid = toBePaid;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public double getPaymentPerDay() {
        return paymentPerDay;
    }

    public void setPaymentPerDay(double paymentPerDay) {
        this.paymentPerDay = paymentPerDay;
    }

    public double getFullDays() {
        return fullDays;
    }

    public void setFullDays(double fullDays) {
        this.fullDays = fullDays;
    }

    public double getHalfDays() {
        return halfDays;
    }

    public void setHalfDays(double halfDays) {
        this.halfDays = halfDays;
    }

    public double getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(double totalDays) {
        this.totalDays = totalDays;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "PaymentWorkerDetailsDTO{" +
                "workerName='" + workerName + '\'' +
                ", categories=" + categories +
                ", paymentPerDay=" + paymentPerDay +
                ", fullDays=" + fullDays +
                ", halfDays=" + halfDays +
                ", totalDays=" + totalDays +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                ", paid=" + paid +
                ", toBePaid=" + toBePaid +
                '}';
    }
}
