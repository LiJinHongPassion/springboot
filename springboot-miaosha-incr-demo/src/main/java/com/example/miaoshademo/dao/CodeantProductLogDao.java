package com.example.miaoshademo.dao;

import com.example.miaoshademo.entity.CodeantProductLog;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeantProductLogDao {
    int insert(CodeantProductLog record);

    CodeantProductLog selectByPrimaryKey(Integer id);
}