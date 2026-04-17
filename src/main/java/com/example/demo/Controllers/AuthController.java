package com.example.demo.Controllers;


import com.example.demo.Model.Users;
import com.example.demo.Service.OtpService;
import com.example.demo.Service.UserService;
import com.example.demo.dtoUser.LoginReq;
import com.example.demo.dtoUser.OtpReq;
import com.example.demo.dtoUser.RegisterRequest;
import com.example.demo.repo.OTPRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserService userservice;

    @Autowired
    private OtpService otpService;

    @Autowired
    private OTPRepo otpRepo;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request){
        otpService.register(request.getEmail());
        return "OTP Sent";
    }

    @PostMapping("/verifyOTP")
    public String verify(@RequestBody OtpReq otpReq){
        return otpService.verifyOtp(otpReq);
    }

    @PostMapping("/createAccount")
    public Users createAccount(@RequestBody RegisterRequest request){
        if(!otpService.isVerified(request.getEmail())){
            throw new RuntimeException("OTP not verified");
        }
        Users user=userservice.createUser(request);
        otpRepo.delete(otpService.findOtpDetails(request.getEmail()));
        return user;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginReq req){
        return userservice.verify(req);
    }
    
}
