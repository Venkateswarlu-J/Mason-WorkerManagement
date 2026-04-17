package com.example.demo.repo;

import com.example.demo.Model.OtpDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OTPRepo extends JpaRepository<OtpDetails,Integer> {
    public OtpDetails findByEmail(String email);
}
