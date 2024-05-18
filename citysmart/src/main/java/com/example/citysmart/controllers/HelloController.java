package com.example.citysmart.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("sayhello")
    public String sayHello(){
        return "Hello User";
    }


}
