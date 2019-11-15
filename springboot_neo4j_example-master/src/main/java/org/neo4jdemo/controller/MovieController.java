package org.neo4jdemo.controller;

import org.neo4jdemo.model.node.Movie;
import org.neo4jdemo.repository.MovieRepository;
import org.neo4jdemo.service.MovieService;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述：
 *
 * @author LJH
 * @date 2019/11/14-14:41
 * @QQ 1755497577
 */
public class MovieController {

    @Resource
    private MovieService movieService;

    @GetMapping("/getAllMovies")
    public List<Movie> getAddMovies(){
      return movieService.findAll();
    }
}
