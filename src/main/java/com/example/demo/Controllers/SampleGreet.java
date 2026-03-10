package com.example.demo.Controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleGreet {

    @GetMapping("/")
    public String greet(){
        return "Hello dude!";
    }
}
