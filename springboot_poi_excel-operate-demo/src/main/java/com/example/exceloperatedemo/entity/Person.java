package com.example.exceloperatedemo.entity;

import java.util.Date;

public class Person{
        private Integer id;
        private String name;
        private String info;
        private Integer age;
        private String sex;
        private Date birthday;

        public Person(Integer id, String name, String info, Integer age, String sex, Date birthday) {
            this.id = id;
            this.name = name;
            this.info = info;
            this.age = age;
            this.sex = sex;
            this.birthday = birthday;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public Date getBirthday() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }
    }