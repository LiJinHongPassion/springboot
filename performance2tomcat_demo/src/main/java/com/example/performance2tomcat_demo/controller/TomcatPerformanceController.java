package com.example.performance2tomcat_demo.controller;

import com.example.performance2tomcat_demo.service.impl.TomcatPerformanceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TomcatPerformanceController {
    @Autowired
    public TomcatPerformanceImpl tomcatPerformance;

    @GetMapping("/create")
    public String createStudents(){
        return tomcatPerformance.create100NumWait1s();
    }
}
