package com.example.li.springboot_poi_demo_v2.hanlder;

import cn.afterturn.easypoi.handler.inter.IExcelExportServer;
import com.example.li.springboot_poi_demo_v2.dao.DogDao;
import com.example.li.springboot_poi_demo_v2.pojo.Dog;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ExportLoadDataHandler implements IExcelExportServer {

    /**
     * 描述:
     *
     * @author LJH-1755497577 2019/8/3 0:58
     * @param obj 可以传参数，例如数据库每页查询多少
     * @param page 循环查询次数
     * @return java.util.List<java.lang.Object>
     */
    @Override
    public List<Object> selectListForExcelExport(Object obj, int page) {
        //获取数据源
        List<Object> list = new ArrayList<Object>();

        List<Dog> dogsList = DogDao.getDogsList();

        for ( Dog dog : dogsList) {
            list.add(dog);
        }

        if(page>1000){
            System.out.println("page:" + page);
            return null;
        }
        return list;
    }

}