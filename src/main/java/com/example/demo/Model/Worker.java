package com.example.demo.Model;


import com.example.demo.enums.Categories;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.context.annotation.Primary;

@Entity
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long workerID;

    @Column(unique = true)
    String workerName;

    String workerPhone;

    @Enumerated(EnumType.STRING)
    Categories workerCat;

    String workerAddress;

    int payPerDay;

    @Column(columnDefinition = "boolean default true")
    boolean isActive;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "sup_id", nullable = true)
    private Supervisor supervisor;

    @OneToOne
    @JoinColumn(name="user_id")
    private Users user;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public Long getWorkerID() {

        return workerID;
    }

    public void setWorkerID(Long workerID) {
        this.workerID = workerID;
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
        return "Worker{" +
                "workerID=" + workerID +
                ", workerName='" + workerName + '\'' +
                ", workerPhone='" + workerPhone + '\'' +
                ", workerCat='" + workerCat + '\'' +
                ", workerAddress='" + workerAddress + '\'' +
                ", Supervisor_id='" + supervisor + '\'' +
                ", payPerDay=" + payPerDay +
                '}';
    }
}
