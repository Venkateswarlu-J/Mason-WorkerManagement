package com.example.demo.dtoUser;

import com.example.demo.enums.PaymentMode;

import java.time.LocalDateTime;

public class TransactionHistoryDTO {

    private long curPaid;

    private LocalDateTime paidAt;

    private PaymentMode paymentMode;

    private long workerId;

    public long getCurPaid() {
        return curPaid;
    }

    public void setCurPaid(long curPaid) {
        this.curPaid = curPaid;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

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

    @Override
    public String toString() {
        return "TransactionHistoryDTO{" +
                "curPaid=" + curPaid +
                ", paidAt=" + paidAt +
                ", paymentMode=" + paymentMode +
                ", workerId=" + workerId +
                '}';
    }
}
