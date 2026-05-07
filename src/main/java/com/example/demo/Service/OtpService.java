package com.example.demo.Service;

import com.example.demo.Model.OtpDetails;
import com.example.demo.dtoUser.OtpReq;
import com.example.demo.repo.OTPRepo;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;

import java.util.*;

@Service
public class OtpService {

    private final String CHAR_POOL =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                    "abcdefghijklmnopqrstuvwxyz" +
                    "0123456789" +
                    "@#$%!&*";

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    private final SecureRandom random = new SecureRandom();

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private OTPRepo otpRepo;

    @Autowired
    private UserRepo userRepo;

    public String generateOtp(){
        return String.valueOf(new Random().nextInt(900000)+100000);
    }

    public void register(String email) {
        if(userRepo.findByEmail(email)!=null) throw new RuntimeException("Email already exists. Please Login!!");
        String otp=generateOtp();
        sendOtp(email,otp);
    }

    public void sendOtp(String email,String otp){
//        System.out.println(email+" to be sent");
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("OTP request");
        message.setText("OTP ::"+otp+"\n This is only valid for 5 minutes");

        OtpDetails existing = otpRepo.findByEmail(email);
        if(existing != null){
            otpRepo.delete(existing);
        }
        OtpDetails otpDetails=new OtpDetails();
        otpDetails.setEmail(email);
        otpDetails.setOtpVerified(false);
        otpDetails.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        otpDetails.setOtp(otp);
        otpRepo.save(otpDetails);

        javaMailSender.send(message);
    }

    public OtpDetails findOtpDetails(String email){
        return otpRepo.findByEmail(email);
    }

    public Map<String, Object> verifyOtp(OtpReq otpReq){
        OtpDetails otpDetails=findOtpDetails(otpReq.getEmail());

        if(otpDetails==null) return Map.of("success", false, "message", "OTP not generated yet!");

        System.out.println(otpDetails.getEmail()+" "+otpReq.getEmail()+" "+otpDetails.getOtp()+" "+otpReq.getOtp());

//        if(otpDetails.getOtpVerified())
//            return "Already verified";

        if (otpDetails.getOtpExpiry().isBefore(LocalDateTime.now())) {
            return Map.of("success", false, "message", "OTP expired");
        }

        if (!otpDetails.getOtp().equals(otpReq.getOtp())) {
            return Map.of("success", false, "message", "Invalid OTP");
        }

        otpDetails.setOtpVerified(true);
        otpDetails.setOtp("");
        otpRepo.save(otpDetails);

        return Map.of("success", true, "message", "OTP verified successfully");
    }

    public boolean isVerified(String email) {
        OtpDetails otpDetails=findOtpDetails(email);
        return otpDetails!=null&&otpDetails.getOtpVerified();
    }

    public String sendPass(String email,String supName) {
        String tempPass=generatePass();
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Account Creation Notification");
        message.setText("From now you are working under the "+supName+" ! and to track your progress" +
                " use the credentials\n\n\nUsername::"+email+"\nPassword::"+tempPass+"\n" +
                " \n If you have any queries reach out our team! eha.. ");
        javaMailSender.send(message);
        return tempPass;
    }

    private String generatePass() {
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(CHAR_POOL.length());
            password.append(CHAR_POOL.charAt(index));
        }
        return encryptPass(password.toString());
    }
    public String encryptPass(String pass){
        return encoder.encode(pass);
    }
}
