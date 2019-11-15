package org.neo4jdemo.model.relation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.neo4j.ogm.annotation.*;
import org.neo4jdemo.model.node.Movie;
import org.neo4jdemo.model.node.Person;

@Getter
@Setter
@NoArgsConstructor//无参构造函数
@ToString//toString方法,使用注解后就不用重写toString方法
@RelationshipEntity(type = "ACTED_IN")
public class ActIn {
    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String role;

    @StartNode
    private Person person;

    @EndNode
    private Movie movie;


}
