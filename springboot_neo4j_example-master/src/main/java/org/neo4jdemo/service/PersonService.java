package org.neo4jdemo.service;

import org.neo4jdemo.model.result.PersonMovie;
import org.neo4jdemo.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述：Person服务类
 *
 * @author LJH
 * @date 2019/11/14-14:54
 * @QQ 1755497577
 */
@Service
public class PersonService {

    @Resource
    private PersonRepository personRepository;

    /**
     * 描述: 初始化数据，添加事务
     *
     * @author LJH-1755497577 2019/11/14 14:56
     * @param
     * @return void
     */
    @Transactional
    public void createDefalutData(){
        personRepository.createDefalutData();
    }

    public List<PersonMovie> getPersonMoviesbyName(String name) {
        return personRepository.getPersonMoviesbyName(name);
    }
}
