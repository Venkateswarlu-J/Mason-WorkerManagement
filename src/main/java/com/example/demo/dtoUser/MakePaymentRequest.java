package com.example.demo.dtoUser;

import com.example.demo.enums.PaymentMode;

public class MakePaymentRequest {
    private long workerId;
    private long amount;
    private PaymentMode paymentMode;

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "MakePaymentRequest{" +
                "workerId=" + workerId +
                "Payment Mode=" + paymentMode +
                ", amount=" + amount +
                '}';
    }
}
