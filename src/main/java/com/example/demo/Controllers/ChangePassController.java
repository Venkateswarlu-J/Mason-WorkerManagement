package com.example.demo.Controllers;

import com.example.demo.Model.Users;
import com.example.demo.Service.ChangePassService;
import com.example.demo.Service.OtpService;
import com.example.demo.Service.UserService;
import com.example.demo.dtoUser.RegisterRequest;
import com.example.demo.repo.OTPRepo;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChangePassController {


    @Autowired
    private ChangePassService changePassService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OTPRepo otpRepo;

    @Autowired
    private OtpService otpService;

    @PostMapping("/sendOTP")
    public String sendOTP(@RequestBody RegisterRequest request){
        changePassService.sendOTP(request.getEmail());
        return "OTP sent to your mail";
    }
    @PostMapping("/changePassword")
    public String changePass(@RequestBody RegisterRequest request){
        String encPass=userService.encryptPass(request.getPassword());

        Users user=userRepo.findByEmail(request.getEmail());
        user.setPassword(encPass);
        userRepo.save(user);
        otpRepo.delete(otpService.findOtpDetails(request.getEmail()));
        return "Password Changed Successfully";
    }
}
