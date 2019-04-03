package com.example.li.springboot_mybatis_shiro_demo.dao.base;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("SearchDao")
public interface SearchDao {


    public List<Map<String, Object>> searchForeign(@Param("properties") String[] properties, @Param("baseEntity") String baseEntity,
                                                   @Param("joinEntity") String joinEntity,
                                                   @Param("foreignEntity") String[] foreignEntity, @Param("foreingKey") List<String> foreingKeySql,
                                                   @Param("condition") String condition);

    public int getForeignCount(@Param("primaryKey") String primaryKey, @Param("baseEntity") String baseEntity,
                               @Param("joinEntity") String joinEntity,
                               @Param("foreignEntity") String[] foreignEntity, @Param("foreingKey") List<String> foreingKeySql,
                               @Param("condition") String condition);

    public List<Map<String, Object>> sqlWithPageInMysql(@Param("sql") String sql, @Param("groupField") String groupField,
                                                        @Param("orderField") String orderField, @Param("sortMode") String sortMode,
                                                        @Param("startIndex") int startIndex, @Param("pageNum") int pageNum);

    public List<Map<String, Object>> searchWithPageInMysql(@Param("properties") String[] properties, @Param("baseEntity") String baseEntity,
                                                           @Param("joinEntity") String joinEntity,
                                                           @Param("foreignEntity") String[] foreignEntity, @Param("foreingKey") List<String> foreingKeySql,
                                                           @Param("condition") String condition, @Param("groupField") String groupField,
                                                           @Param("orderField") String orderField, @Param("sortMode") String sortMode,
                                                           @Param("startIndex") int startIndex, @Param("pageNum") int pageNum);

    public List<Map<String, Object>> searchWithPageInOracle(@Param("properties") String[] properties, @Param("baseEntity") String baseEntity,
                                                            @Param("joinEntity") String joinEntity,
                                                            @Param("foreignEntity") String[] foreignEntity, @Param("foreingKey") List<String> foreingKeySql,
                                                            @Param("condition") String condition, @Param("groupField") String groupField,
                                                            @Param("orderField") String orderField, @Param("sortMode") String sortMode,
                                                            @Param("startIndex") int startIndex, @Param("endIndex") int endIndex);

    public List<Map<String, Object>> searchWithPage(@Param("properties") String[] properties,
                                                    @Param("baseEntity") String baseEntityName, @Param("joinEntity") String joinEntity, @Param("foreignEntity") String[] foreignEntitys,
                                                    @Param("condition") String condition, @Param("foreingKey") boolean isForeignKey, @Param("groupField") String groupField,
                                                    @Param("orderField") String orderField, @Param("sortMode") String sortMode, @Param("startIndex") int pageNum, @Param("endIndex") int pageIndex);

    public int sqlCount(@Param("sql") String sql);
}
