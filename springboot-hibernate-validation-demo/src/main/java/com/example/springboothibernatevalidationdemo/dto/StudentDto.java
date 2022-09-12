package com.example.springboothibernatevalidationdemo.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import java.util.List;

public class StudentDto {

    @Null
    @NotEmpty
    private String name;
    private Integer age;
    private String address;
    private List<String> score;
}
