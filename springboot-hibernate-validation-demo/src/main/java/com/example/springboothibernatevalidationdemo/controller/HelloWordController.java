package com.example.springboothibernatevalidationdemo.controller;

import com.example.springboothibernatevalidationdemo.dto.StudentDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("student")
public class HelloWordController {

    @PostMapping
    public String addStudent(@RequestBody StudentDto dto){
        return "";
    }
}
