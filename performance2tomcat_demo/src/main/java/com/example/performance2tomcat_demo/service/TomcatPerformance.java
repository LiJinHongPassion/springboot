package com.example.performance2tomcat_demo.service;

import com.example.performance2tomcat_demo.entity.Student;
import com.example.performance2tomcat_demo.service.impl.TomcatPerformanceImpl;
import org.openjdk.jol.info.ClassLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TomcatPerformance implements TomcatPerformanceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(TomcatPerformance.class);

    @Override
    public String create100NumWait1s() {
        int num = 200000;
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Student student = new Student();
            // 打印student占用内存 -- 24byte
//             LOGGER.info(ClassLayout.parseInstance(student).toPrintable());
            list.add(student);
        }
        // 打印student占用内存 --
//        LOGGER.info(ClassLayout.parseInstance(list).toPrintable());

        // 睡眠1s
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        list.remove(1);
        LOGGER.info("api finished");
        return "ok";
    }
}
