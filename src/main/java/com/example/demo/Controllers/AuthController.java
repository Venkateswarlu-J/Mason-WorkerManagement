package com.example.demo.Controllers;

import org.springframework.http.ResponseEntity;
import com.example.demo.Model.*;
import com.example.demo.Service.OtpService;
import com.example.demo.Service.UserService;
import com.example.demo.dtoUser.*;
import com.example.demo.dtoUser.OtpReq;
import com.example.demo.dtoUser.RegisterRequest;
import com.example.demo.repo.OTPRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.lang.*;
import java.util.*;

@RestController
public class AuthController {
    @Autowired
    private UserService userservice;

    @Autowired
    private OtpService otpService;

    @Autowired
    private OTPRepo otpRepo;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
//        System.out.println("hi");
        try{
            otpService.register(request.getEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("OTP Sent");
    }

    @PostMapping("/verifyOTP")
    public Map<String, Object> verify(@RequestBody OtpReq otpReq){
        return otpService.verifyOtp(otpReq);
    }

    @PostMapping("/createAccount")
    public ResponseEntity<?> createAccount(@RequestBody RegisterRequest request){
        if (request.getEmail() == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", "Email is required")
            );
        }
        if(!otpService.isVerified(request.getEmail())){
            return ResponseEntity.badRequest().body(Map.of("success",false,
                    "data","OTP not verified"));
        }
        Users user=userservice.createUser(request);
        otpRepo.delete(otpService.findOtpDetails(request.getEmail()));
        return ResponseEntity.ok(Map.of("success",true,
                "data",user));
    }

    @PostMapping("/login")
    public LoginRes login(@RequestBody LoginReq req){
        return userservice.verify(req);
    }
    
}
