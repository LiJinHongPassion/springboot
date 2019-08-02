package com.example.li.springboot_poi_demo_v2.service;

import com.example.li.springboot_poi_demo_v2.pojo.Dog;

import java.util.List;
import java.util.Map;

/**
        * @author LJH
        * @date 2019/8/2-18:59
        * @QQ 1755497577
        */
public interface IDogService {

    List<Dog> getDogsList();

    List<Map<String, Object>> getDogsListMap();

}
