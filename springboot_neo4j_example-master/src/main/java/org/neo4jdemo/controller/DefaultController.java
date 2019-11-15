package org.neo4jdemo.controller;

import org.neo4jdemo.repository.MovieRepository;
import org.neo4jdemo.repository.PersonRepository;
import org.neo4jdemo.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 描述：初始化数据
 *
 * @author LJH
 * @date 2019/11/15-17:21
 * @QQ 1755497577
 */
@Controller
public class DefaultController {

    @Resource
    private PersonRepository personRepository;
    @Resource
    private MovieRepository movieRepository;
    @Resource
    private PersonService personService;

    /*初始化数据*/
    @RequestMapping("/create")
    public String createDefalutData(){
        personRepository.deleteAll();
        movieRepository.deleteAll();
        personService.createDefalutData();
        return "suc";
    }

    /*删除所有数据*/
    @RequestMapping("/delete")
    public String delete(){
        personRepository.deleteAll();
        movieRepository.deleteAll();
        return "suc";
    }

    /*删除人物所有数据*/
    @RequestMapping("/deleteP")
    public String deleteP(){
        personRepository.deleteAll();
        return "suc";
    }
    /*删除电影所有数据*/
    @RequestMapping("/deleteM")
    public String deleteM(){
        movieRepository.deleteAll();
        return "suc";
    }
}
