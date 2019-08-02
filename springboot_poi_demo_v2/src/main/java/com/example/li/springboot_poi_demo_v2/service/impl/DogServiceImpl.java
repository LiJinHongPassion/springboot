package com.example.li.springboot_poi_demo_v2.service.impl;

import com.example.li.springboot_poi_demo_v2.dao.DogDao;
import com.example.li.springboot_poi_demo_v2.pojo.Dog;
import com.example.li.springboot_poi_demo_v2.service.IDogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author LJH
 * @date 2019/8/2-18:58
 * @QQ 1755497577
 */
@Service
public class DogServiceImpl implements IDogService {

    @Override
    public List<Dog> getDogsList() {
        return DogDao.getDogsList();
    }

    @Override
    public List<Map<String, Object>> getDogsListMap() {
        return DogDao.getDogsListMap();
    }
}
