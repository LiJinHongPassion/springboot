package org.neo4jdemo.service;

import org.neo4jdemo.model.node.Movie;
import org.neo4jdemo.repository.MovieRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述：Movie服务类
 *
 * @author LJH
 * @date 2019/11/14-14:54
 * @QQ 1755497577
 */
@Service
public class MovieService {

    @Resource
    private MovieRepository movieRepository;

    public List<Movie> findAll() {
        return (List<Movie>) movieRepository.findAll();
    }

}
