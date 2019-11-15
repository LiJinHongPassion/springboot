package org.neo4jdemo.service;

import org.neo4jdemo.model.node.Person;
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
    @Transactional(rollbackFor = Exception.class)
    public void createDefalutData(){
        personRepository.createDefalutData();
    }

    /**
     * 描述: 通过名字获取电影
     *
     * @author LJH-1755497577 2019/11/15 17:17
     * @param name
     * @return java.util.List<org.neo4jdemo.model.result.PersonMovie>
     */
    public List<PersonMovie> getPersonMoviesbyName(String name) {
        return personRepository.getPersonMoviesbyName(name);
    }

    /**
     * 描述: 添加一位人员
     *
     * @author LJH-1755497577 2019/11/15 17:16
     * @param person
     * @return org.neo4jdemo.model.node.Person
     */
    @Transactional(rollbackFor = Exception.class)
    public Person savePerson(Person person) {
        return personRepository.save(person);
    }
}
