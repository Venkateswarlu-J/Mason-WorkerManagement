package com.example.demo.Controllers;


import com.example.demo.Model.Users;
import com.example.demo.Service.UserService;
import com.example.demo.dtoUser.LoginReq;
import com.example.demo.dtoUser.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserService userservice;

    @PostMapping("/register")
    public Users register(@RequestBody RegisterRequest request){
        return userservice.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginReq req){
        return userservice.verify(req);
    }
    
}
