package org.neo4jdemo.model.result;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4jdemo.model.node.Movie;
import org.neo4jdemo.model.node.Person;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@QueryResult
public class PersonMovie {
    private Person person;

    private Set<Movie> movies;


}
