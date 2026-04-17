package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChangePassService {

    @Autowired
    private OtpService otpService;

    public void sendOTP(String email){
        String otp= otpService.generateOtp();
        otpService.sendOtp(email,otp);
    }
}
