package org.neo4jdemo.model.node;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.neo4j.ogm.annotation.*;
import org.neo4jdemo.model.relation.ActIn;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor//无参构造函数
@ToString//toString方法,使用注解后就不用重写toString方法
@NodeEntity(label = "Movie")
public class Movie {
    @Id
    @GeneratedValue
    private Long id;

    @Property
    private int released;

    @Property
    private String tagline;

    @Property
    private String title;

    @Relationship(direction = Relationship.INCOMING, type = "ACTED_IN")
    private Set<ActIn> actIns;


}
