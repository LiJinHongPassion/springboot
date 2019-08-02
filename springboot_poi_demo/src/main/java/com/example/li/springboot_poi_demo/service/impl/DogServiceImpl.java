package com.example.li.springboot_poi_demo.service.impl;

import com.example.li.springboot_poi_demo.dao.DogDao;
import com.example.li.springboot_poi_demo.pojo.Dog;
import com.example.li.springboot_poi_demo.service.IDogService;
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
}
