package com.example.exceloperatedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExcelOperateDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExcelOperateDemoApplication.class, args);
        System.out.println("导出 : http://localhost:8082/codeAntExcel/export");
        System.out.println("导入 : http://localhost:8082/codeAntExcel/upload.html");
    }

}
