package org.neo4jdemo.controller;

import org.neo4jdemo.model.node.Movie;
import org.neo4jdemo.repository.MovieRepository;
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
    private MovieRepository movieRepository;

    /**
     * 描述: 获取所有的电影
     *
     * @author LJH-1755497577 2019/11/15 17:22
     * @param
     * @return java.util.List<org.neo4jdemo.model.node.Movie>
     */
    @GetMapping("/getAllMovies")
    public List<Movie> getAddMovies(){
      return (List<Movie>) movieRepository.findAll();
    }
}
