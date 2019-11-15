package org.neo4jdemo.model.node;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4jdemo.model.relation.ActIn;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor//无参构造函数
@ToString//toString方法,使用注解后就不用重写toString方法
@NodeEntity(label = "Person")
public class Person {
    @GraphId
    private Long id;

    @Property
    private int born;

    @Property
    private String name;

    @Relationship(direction = Relationship.OUTGOING, type = "ACTED_IN")
    private Set<ActIn> ActInList;



}
