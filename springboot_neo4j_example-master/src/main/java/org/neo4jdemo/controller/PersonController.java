package org.neo4jdemo.controller;

import org.neo4jdemo.model.result.PersonMovie;
import org.neo4jdemo.repository.MovieRepository;
import org.neo4jdemo.repository.PersonRepository;
import org.neo4jdemo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class PersonController {
    @Resource
    private PersonService personService;


    /*http://localhost:8080/rest/v1/person?name=Demi%20Moore*/
    @GetMapping(path = "/rest/v1/person")
    public List<PersonMovie> getMoviesByPersonName(@RequestParam String name) {
        return personService.getPersonMoviesbyName(name);
    }

//    @GetMapping(path = "/rest/v2/person")
//    public List<Person> getPersonRels(@RequestParam String name) {
//        List<Person> people = personRepository.getPersonByName(name);
//        // you can process the people object in java
//        return people;
//    }

    /*初始化数据*/
    @RequestMapping("/create")
    public String createDefalutData(){
        personService.createDefalutData();
        return "suc";
    }

    @Resource
    private PersonRepository personRepository;
    @Resource
    private MovieRepository movieRepository;
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
