package org.neo4jdemo.repository;

import org.neo4jdemo.model.node.Person;
import org.neo4jdemo.model.result.PersonMovie;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 描述: Person的dao层，继承Neo4jRepository，就自带crud
 *
 * @author LJH-1755497577 2019/11/14 15:02
 */
public interface PersonRepository extends Neo4jRepository<Person, Long> {

    @Query("MATCH (person:Person {name: {name}})-[r:ACTED_IN]->(movie) RETURN person,collect(movie) as movies")
    List<PersonMovie> getPersonMoviesbyName(@Param("name") String name);

    /*生成默认数据*/
    @Query("CREATE (TheMatrix:Movie {title:'The Matrix', released:1999, tagline:'Welcome to the Real World'})\n" +
            "    CREATE (Keanu:Person {name:'Keanu Reeves', born:1964})\n" +
            "    CREATE (Carrie:Person {name:'Carrie-Anne Moss', born:1967})\n" +
            "    CREATE (Laurence:Person {name:'Laurence Fishburne', born:1961})\n" +
            "    CREATE (Hugo:Person {name:'Hugo Weaving', born:1960})\n" +
            "    CREATE (LillyW:Person {name:'Lilly Wachowski', born:1967})\n" +
            "    CREATE (LanaW:Person {name:'Lana Wachowski', born:1965})\n" +
            "    CREATE (JoelS:Person {name:'Joel Silver', born:1952})\n" +
            "    CREATE\n" +
            "            (Keanu)-[:ACTED_IN {roles:['Neo']}]->(TheMatrix),\n" +
            "            (Carrie)-[:ACTED_IN {roles:['Trinity']}]->(TheMatrix),\n" +
            "            (Laurence)-[:ACTED_IN {roles:['Morpheus']}]->(TheMatrix),\n" +
            "            (Hugo)-[:ACTED_IN {roles:['Agent Smith']}]->(TheMatrix),\n" +
            "            (LillyW)-[:DIRECTED]->(TheMatrix),\n" +
            "            (LanaW)-[:DIRECTED]->(TheMatrix),\n" +
            "            (JoelS)-[:PRODUCED]->(TheMatrix)")
    void createDefalutData();

}
