package org.neo4jdemo.controller;

import org.neo4jdemo.model.node.Person;
import org.neo4jdemo.model.result.PersonMovie;
import org.neo4jdemo.service.PersonService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class PersonController {
    @Resource
    private PersonService personService;

    /**
     * 描述:通过人物名查看他演过的电影
     *      http://localhost:8080/rest/v1/person?name=Keanu
     *
     * @author LJH-1755497577 2019/11/15 17:06
     * @param name
     * @return java.util.List<org.neo4jdemo.model.result.PersonMovie>
     */
    @GetMapping(path = "/rest/v1/person")
    public List<PersonMovie> getMoviesByPersonName(@RequestParam String name) {
        return personService.getPersonMoviesbyName(name);
    }


    /**
     * 描述: 保存人员
     *
     * @author LJH-1755497577 2019/11/15 17:17
     * @param person
     * @return org.neo4jdemo.model.node.Person
     */
    @PostMapping("/savePerson")
    public Person savePerson(@RequestBody Person person){
        return personService.savePerson(person);
    }





}
