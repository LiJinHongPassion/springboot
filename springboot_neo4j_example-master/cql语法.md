## 1 语法

#### 1.1 节点语法

Cypher 采用一对圆括号来表示节点。如:（）, (foo)。 下面是一些常见的节点表示法：

```text
//格式：(变量名:标签1:标签2:...{属性1:属性值1，属性2:属性值2，....})
()
(matrix)
(:Movie)
(matrix:Movie)
(matrix:Movie {title: "The Matrix"})
(matrix:Movie {title: "The Matrix", released: 1997})
```

#### 1.2 关系语法

Cypher使用一对短横线（即"--"）表示: 一个无方向关系。有方向的关系在其中-一段加上一个箭头(即"<--"或“-->”)。方括号  [表达式..] 可用于添加详情。里面可以包含变量、属性和或者类型信息。关系的常见表达方式如下：

```text
//说明：节点-[关系变量名:关系类型{属性1:属性值1，属性2:属性值2，....}]->节点
--
-->
-[role]->
-[:ACTED_ IN]->
-[role:ACTED_IN]->
-[role:ACTED_IN {roles: ["Neo"]}]->
```

`match (n)-[r]->(m) return r,n,m;`

#### 1.3 模式语法

将节点和关系的语法组合在一起可以表达模式。

```text
(keanu: Person:Actor {name: "Keanu Reeves"})
-[role:ACTED_ IN {roles:["Neo"]}]->
(matrix:Movie {title:"The Matrix"} )
```

## 2 CQL语法

#### 语法简介

 https://www.w3cschool.cn/neo4j/neo4j_cql_introduction.html 

#### 2.1 [create命令]( https://www.w3cschool.cn/neo4j/neo4j_cql_create_label.html )

```text
//创建一个老师和学生节点，具体属性见下;
//学生和老师之间的关系是授课，关系的属性见下；09
create (n:Student{name:"tom",age:23, type:"Student"})<-[teach:Teach{class:"English"}]-(teacher:Teacher{name:"kk",age:33,type:"Teacher"});

match (n:Student{name:"tom"}),(t:Teacher) where t.name="kk" create (m:Student{name:"mikle"})<-[rt:Teach{class:"Chinese"}]-(t);
```

#### 2.2 match命令

```text
//match后的都需要匹配
//匹配返回关系
match (n:Student{name:"tom"})<-[r]-(m) return r;
//匹配节点标签为Student的所有节点，并返回
match (n:Student) return n;
match (n:Student{name:"tom"}),(m:Student{name:"mikle"}) return n,m;
match (n:Student) where n.name="tom" OR n.name="mikle" return n;
//匹配节点标签为Student，name为“tom”的所有节点，并返回
match (n:Student{name:"tom"}) return n;
//匹配节点标签为Student，name为“tom”的所有节点，并返回
match (n:Student{name:"tom"})<-[*]-(m) return n;
//匹配节点标签为Student，name为“tom”的所有节点，并且具有Teach类型的关系，并且Teacher节点name为“kk”，返回n
match (n:Student{name:"tom"})<-[:Teach]-(m:Teacher{name:"kk"}) return n;
```

#### 2.3 return子句

```text
//语法
RETURN 
   <node-name>.<property1-name>,
   ........
   <node-name>.<propertyn-name>
```

```text
return n;
return n.name;
```

#### 2.4 [where子句]( https://www.w3cschool.cn/neo4j/neo4j_cql_where_clause.html )

```text
//语法
# 复杂WHERE子句语法
WHERE <condition> <boolean-operator> <condition>
# <condition>语法
<property-name> <comparison-operator> <value>
```

**<boolean-operator>说明**

| S.No. | 布尔运算符 | 描述                                   |
| ----- | ---------- | -------------------------------------- |
| 1     | AND        | 它是一个支持AND操作的Neo4j CQL关键字。 |
| 2     | OR         | 它是一个Neo4j CQL关键字来支持OR操作。  |
| 3     | NOT        | 它是一个Neo4j CQL关键字支持NOT操作。   |
| 4     | XOR        | 它是一个支持XOR操作的Neo4j CQL关键字。 |

**<comparison-operator>说明**

| S.No. | 布尔运算符 | 描述                                  |
| ----- | ---------- | ------------------------------------- |
| 1.    | =          | 它是Neo4j CQL“等于”运算符。           |
| 2.    | <>         | 它是一个Neo4j CQL“不等于”运算符。     |
| 3.    | <          | 它是一个Neo4j CQL“小于”运算符。       |
| 4.    | >          | 它是一个Neo4j CQL“大于”运算符。       |
| 5.    | <=         | 它是一个Neo4j CQL“小于或等于”运算符。 |
| 6.    | >=         | 它是一个Neo4j CQL“大于或等于”运算符。 |

**例子**

```text
match (n:Student) where n.name="tom" return n;
```

#### 2.5 delete子句

##### 2.5.1 [删除节点]( https://www.w3cschool.cn/neo4j/delete.html )

```text
//DELETE节点子句语法
DELETE <node-name-list>
```

```text
//若是当前要删除的节点是单一节点，可以直接删除
//1. 检查是否为单一节点
match (n:Student{name:"tom"})-[r]-(m) return r;
//2. 若是结果为空列表，直接删除
match (n:Student{name:"tom"}) delete n; 	<==>	match (n:Student) where n.name="tom" delete n;
```

##### 2.5.2 删除关系

```text
//删除类型为：Same的关系
match (m)-[s:Same]-(n) delete s
```

##### 2.5.3 删除节点以及节点之间的关系

```text
//DELETE节点和关系子句语法
DELETE <node1-name>,<node2-name>,<relationship-name>
```

```text
//匹配节点和节点的关系，删除节点以及关系
match (n:Student)-[r]-(m) where n.name="tom" delete n,r;
//等价于
match (n:Student{name:"tom"})-[r]-(m) delete n,r;
```

####  2.6 [remove子句]( https://www.w3cschool.cn/neo4j/neo4j_cql_remove.html )

DELETE和REMOVE命令之间的主要区别 

- DELETE操作用于删除节点和关联关系。
- REMOVE操作用于删除标签和属性。

##### 2.6.1 删除节点或关系的标签



```text
MATCH (m:Movie) REMOVE m:Picture
```



##### 2.6.2 删除节点或关系的属性

**节点**

```text
//创建book节点
CREATE (book:Book {id:122,title:"Neo4j Tutorial",pages:340,price:250}) 
//删除节点属性
MATCH (book { id:122 }) REMOVE book.price RETURN book
```

**关系**

```text
//再创建一个book节点，并与之前的book 122创建关系，关系为：相似（same）
match (b:Book) 
where b.id=122 
CREATE (book:Book {id:133,title:"Neo4j Tutorial1111 ",pages:341,price:350})-[s:Same{name:"same", id:232,test:"test"}]->(b)
//删除节点属性
MATCH (s:Same) REMOVE s.test RETURN s
```

#### 2.7 set子句

```text
MATCH (dc:DebitCard)
SET dc.atm_pin = 3456
RETURN dc
```

#### 2.8 order by子句

```
MATCH (emp:Employee)
RETURN emp.empid,emp.name,emp.salary,emp.deptno
ORDER BY emp.name DESC
```

#### 2.9 union子句

**限制：**

结果列类型和来自两组结果的名称必须匹配，这意味着列名称应该相同，列的数据类型应该相同。

```text
//语法
<MATCH Command1>
   UNION
<MATCH Command2>
```

```
//例子
MATCH (cc:CreditCard) RETURN cc.id,cc.number
UNION
MATCH (dc:DebitCard) RETURN dc.id,dc.number
```

#### 2.10 limit和skip子句

**limit和skip的区别**

-  limit它修剪CQL查询结果集底部的结果 
- skip 修整CQL查询结果集顶部的结果 

具体看 https://www.w3cschool.cn/neo4j/neo4j_cql_limit_skip.html 

**limit**

```text
//语法
LIMIT <number>
//例子
MATCH (emp:Employee) 
RETURN emp
LIMIT 2
```

**skip**： “SKIP”子句来过滤或限制查询返回的行数 

```text
//语法
SKIP <number>
//例子
MATCH (emp:Employee) 
RETURN emp
SKIP 2
```

#### 2.11 [merge子句 - 合并]( https://www.w3cschool.cn/neo4j/neo4j_cql_merge.html )

**作用：** CQL MERGE命令检查该节点在数据库中是否可用。 如果它不存在，它创建新节点。 否则，它不创建新的。 

因为create不管你创建的节点是否重复，他都会创建新的节点

Neo4j使用CQL MERGE命令

- 创建节点，关系和属性
- 为从数据库检索数据

```text
MERGE = CREATE + MATCH
```

```text
//例子语法
MERGE (gp2:GoogleProfile2{ Id: 201402,Name:"Nokia"})
```

## 3 CQL组合

#### 3.1 检索

match命令 + return子句 + [order by] + [limit | skip]

match命令 + [where子句] + return子句 + [  (order by) + [limit | skip]  ] 

```text
match (n:Student) where n.name="tom" return n;
```

match命令 + return子句 + union子句 + match命令 + return子句 + [order by] + [limit | skip]

```text
//例子
MATCH (cc:CreditCard) RETURN cc.id,cc.number
UNION
MATCH (dc:DebitCard) RETURN dc.id,dc.number
```



#### 3.2 添加

create命令

match命令 + where子句 + create命令

```text
match (n:Student{name:"tom"}),(t:Teacher) 
where t.name="kk" 
create (m:Student{name:"mikle"})<-[rt:Teach{class:"Chinese"}]-(t);
```

merge子句

```text
MERGE (gp2:GoogleProfile2{ Id: 201402,Name:"Nokia"})
```



#### 3.3 删除

match命令 + [where子句] + delete子句

match命令 + [where子句] + remove子句 + [   return子句 + [  (order by) + [limit | skip]  ]  ]



#### 3.4 修改

match命令 + [where子句] + set子句 + [   return子句 + [  (order by) + [limit | skip]  ]  ]



## 4 CQL函数

#### 4.1 字符串函数
参照：https://www.w3cschool.cn/neo4j/neo4j_cql_string_functions.html
#### 4.2 AGGREGATION聚合
参照：https://www.w3cschool.cn/neo4j/neo4j_cql_aggregation_functions.html
#### 4.3 关系函数
参照：https://www.w3cschool.cn/neo4j/neo4j_cql_relationship_functions.html