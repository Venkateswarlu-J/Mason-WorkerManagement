package com.example.demo.Model;

import com.example.demo.enums.PaymentMode;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;

@Entity
public class TransactionHistory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tId;

    @JsonIgnore
    @ManyToOne
    private Worker worker;

    @JsonIgnore
    @ManyToOne
    private Supervisor supervisor;

    private long curPaid;

    private LocalDateTime paidAt;

    private PaymentMode paymentMode;

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public long getId() {
        return tId;
    }

    public void settId(long tId) {
        this.tId = tId;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

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

    @Override
    public String toString() {
        return "TransactionHistory{" +
                "tId=" + tId +
                ", worker=" + worker +
                ", supervisor=" + supervisor +
                ", curPaid=" + curPaid +
                ", paidAt=" + paidAt +
                ",Payment Mode=" + paymentMode +
                '}';
    }
}
