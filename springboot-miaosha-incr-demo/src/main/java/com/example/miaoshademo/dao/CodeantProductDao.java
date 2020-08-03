package com.example.miaoshademo.dao;

import com.example.miaoshademo.entity.CodeantProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeantProductDao {
    CodeantProduct selectByPrimaryKey(Integer id);

    int update(@Param("num") int num, @Param("key") int key);
}