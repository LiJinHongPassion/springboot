package com.example.li.springboot_poi_demo.dao;

import com.example.li.springboot_poi_demo.pojo.Dog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述：dog的数据源
 * @author LJH
 * @date 2019/8/2-19:03
 * @QQ 1755497577
 */
public class DogDao {

    public static List<Dog> dog_list = new ArrayList<>();

    static {
        Dog d1 = new Dog("宝财",
                "雌性",
                (int) (Math.random()*99+1),
                (float)(Math.random()*99+51),
                "中华田园犬");
        Dog d2 = new Dog("小芳",
                "雌性",
                (int) (Math.random()*99+1),
                (float)(Math.random()*99+51),
                "贵妃犬");
        Dog d3 = new Dog("大雄",
                "雄性",
                (int) (Math.random()*99+1),
                (float)(Math.random()*99+51),
                "英国斗牛犬");
        Dog d4 = new Dog("二哈",
                "雌性",
                (int) (Math.random()*99+1),
                (float)(Math.random()*99+51),
                "拆家犬");


        dog_list.add(d1);
        dog_list.add(d2);
        dog_list.add(d3);
        dog_list.add(d4);

    }

    /**
     * 描述: 模拟从数据源获取List<Dog>数据
     *
     * @author LJH-1755497577 2019/8/2 19:59
     * @param
     * @return java.util.List<com.example.li.springboot_poi_demo.pojo.Dog>
     */

    public static List<Dog> getDogsList(){
        return dog_list;
    }

}
