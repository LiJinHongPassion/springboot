# ES入门 - 索引、映射、文档的增删改查：
## 1. 三大概念：
   - 索引 -- 数据库
   - 映射 -- 表
   - 文档 -- 一行数据
   - 字段 -- 字段


## 2. 索引
#### a. 创建索引
   PUT /{索引名}
   PUT /code_ant

#### b. 查看索引列表
   GET /_cat/indices?v

#### c. 删除索引
   DELETE /{索引名}
   DELETE /code_ant

#### d. 没有更新索引操作
   可先删除，再新增

## 3. 映射
#### a. 创建索引 && 创建映射；索引存在会报错
   PUT /code_ant
```json
{
   ## 分片信息配置
   "settings":{
   "number_of_shards": 1,
   "number_of_replicas": 0
   },
   ## 映射配置
   "mappings": {
       "properties": {
           "id": {
               ## 数据类型
               "type": "integer"
           },
           "title": {
               ## 数据类型
               "type": "keyword"
           },
           "price": {
              "type": "double"
           },
           "created_at": {
              "type": "date"
           },
           "discription": {
              "type": "text"
           }
       }
   }
}
```
   
数据类型
   - 字符串：keyword（关键字\词，不会分词）、text（一段文本，会分词）
   - 数字类型： 整数（integer\long）小数（float\double）
   - bool类型： boolean
   - 日期： date

#### b. 查看索引的映射信息
   GET /{索引名}/_mapping
   GET /code_ant/_mapping

#### c. 不允许删除

#### d. 允许增量修改映射，已有字段不能修改

## 4. 文档
#### a. 新增文档
   POST /{索引名}/_doc/{文档ID} ##手动指定ID
   POST /{索引名}/_doc/ ##自动生成ID
```json
POST /code_ant/_doc/1
   	{
   		"id": 1,
   		"title": "这是标题",
   		"price": 12.5,
   		"created_at": "2022-01-01",
   		"discription": "这是描述内容"
   	}

POST /code_ant/_doc/
{
    "title": "这是标题2",
    "price": 12.5,
    "created_at": "2022-01-01",
    "discription": "这是描述内容2"
}

```

#### b. 查询单条文档
```json
GET /{索引名}/_doc/{文档ID}

GET /code_ant/_doc/1
```

#### c. 删除单条文档
```json
DELETE /{索引名}/_doc/{文档ID}

DELETE /code_ant/_doc/1
```

#### d. 更新单条文档
```json
PUT /{索引名}/_doc/{文档ID}  ## 先删除原始数据，再添加数据，但是只会有更新后的字段
{
  更新字段: 更新值
}
PUT /code_ant/_doc/1
{
  "title": "更新标题"
}

## 基于指定字段更新
POST /{索引名}/_doc/{文档ID}/_update
{
    "doc": {
      更新字段: 更新值
    }
}


POST /code_ant/_doc/1/_update
{
    "doc": {
       "title": "更新标题2"
    }
}
```
		
#### e. 文档批量操作_bulk(对象里面的属性不能换行)
```json
POST /{索引名}/_doc/_bulk

POST /code_ant/_doc/_bulk
## 批量新增 
{"index":{ "_id": 2}}
    { "id": 2, "title": "这是标题2", "price": 12.15, "created_at": "2022-02-01", "discription": "这是描述内容2" }
{"index":{ "_id": 3}}
    { "id": 3, "title": "这是标题3", "price": 12.35, "created_at": "2022-03-01", "discription": "这是描述内容3" }
## 批量更新
{"update":{ "_id": 2}}
    { "doc":{ "title": "这是标题2更新"}}
{"update":{ "_id": 3}}
    { "doc":{"title": "这是标题2更新", "discription": "这是描述内容3更新"}}
## 批量删除
{"delete":{ "_id": 2}}

```

# ES高级搜索
Query DSL：通过restful api，传递json数据，完成查询交互

```json
语法：
GET /{索引名}[/_doc]/_search
    # JSON查询体
    {
    "query":{
            # ... 查询条件
			## 匹配所有
			"match_all": {
				# ...条件，不填写则是所有
			}
		}
	}
```

## 常见检索
#### 查询所有 - match_all
```json
GET /{索引名}/_search
# JSON查询体
{
    "query":{
      "match_all": {}
    }
}

GET /code_ant/_search
# JSON查询体
{
    "query":{
      "match_all": {}
    }
}
```

#### 关键字搜索 - term
keyword、integer、double、date、boolean数据类型：不分词，需要完全匹配，如果值为苹果，需要输入苹果进行搜索，输入苹搜不了
text数据类型：分词，默认standard模式：中文单字分词，英文单词分词
总结：
1. 除了text分词，其他均不分词
2. 默认standerd分词器：中文单字分词，英文单词分词
```json

GET /{索引名}/_search
# JSON查询体
{
    "query":{
        "term": {
            "字段名": {
              "value": 字段值
            }
        }
    }
}

GET /code_ant/_search
{
    "query":{
        "term": {
            "title": {
              "value": "更新标题2"
            }
        }
    }
}
GET /code_ant/_search
{
    "query":{
        "term": {
            "price": {
              "value": 12.5
            }
        }
    }
}
GET /code_ant/_search
{
    "query":{
        "term": {
            "discription": {
              "value": "这"
            }
        }
    }
}

```


#### 范围查询 - range
```json
GET /{索引名}/_search
# JSON查询体
{
    "query":{
        "range": {
            "字段名": {
                "gte": 字段值,
                "lte": 字段值
            }
        }
    }
}

GET /code_ant/_search
{
    "query":{
        "range": {
            "price": {
                "gte": 0,
                "lte": 12.2
            }
        }
    }
}

```

#### 关键词前缀查询 - prefix
```json

注意：是关键词前缀
GET /{索引名}/_search
# JSON查询体
{
    "query":{
        "prefix": {
            "字段名": {
                "gte": 字段值,
                "lte": 字段值
            }
        }
    }
}

GET /code_ant/_search
{
    "query":{
        "prefix": {
            "title": {
              "value": "这是"
            }
        }
    }
}
GET /code_ant/_search
{
    "query":{
        "prefix": {
            "discription": {
              "value": "是"
            }
        }
    }
}
```


#### 通配符查询 - wildcard
? : 匹配一个任意字符
* ：匹配多个任意字符
```json

GET /{索引名}/_search
# JSON查询体
{
    "query":{
        "wildcard": {
            "字段名": {
              "value": 表达式
            }
        }
    }
}

GET /code_ant/_search
{
    "query":{
        "wildcard": {
            "title": {
              "value": "?是*"
            }
        }
    }
}
```

#### 多ID查询 - ids
```json

查询一组符合条件的id
GET /{索引名}/_search
# JSON查询体
{
    "query":{
        "ids": {
          "values": [id的数组]
        }
    }
}
GET /code_ant/_search
{
    "query":{
        "ids": {
          "values": [1,3]
        }
    }
}

```