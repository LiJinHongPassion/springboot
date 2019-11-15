package org.neo4jdemo.repository;

import org.neo4jdemo.model.node.Movie;
import org.springframework.data.neo4j.repository.Neo4jRepository;


/**
 * 描述：Movie的dao层，继承Neo4jRepository，就自带crud
 *
 * @author LJH
 * @date 2019/11/14-14:37
 * @QQ 1755497577
 */
public interface MovieRepository extends Neo4jRepository<Movie, Long> {

}
