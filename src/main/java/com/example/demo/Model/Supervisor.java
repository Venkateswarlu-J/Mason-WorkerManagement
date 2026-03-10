package com.example.demo.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.*;

@Entity
public class Supervisor {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long supId;
    @Column(unique = true)
    String sup_name;
    String phone;
    @OneToOne
    @JoinColumn(name="user_id",unique = true, nullable = false)
    private Users user;//ref in the users table it tels the this is the foreign key to users table

    @OneToMany(mappedBy = "supervisor" ,cascade=CascadeType.ALL)
    @JsonBackReference
    private List<Worker> worker;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Long getSupId() {
        return supId;
    }

    public void setSupId(Long supId) {
        this.supId = supId;
    }

    public String getSup_name() {
        return sup_name;
    }

    public void setSup_name(String sup_name) {
        this.sup_name = sup_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Supervisor{" +
                "supId=" + supId +
                ", sup_name='" + sup_name + '\'' +
                ", phone='" + phone + '\'' +
                ", user='" + user + '\'' +
                '}';
    }


}
