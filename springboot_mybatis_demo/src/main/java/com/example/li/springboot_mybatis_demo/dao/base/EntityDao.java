package com.example.li.springboot_mybatis_demo.dao.base;

import com.example.li.springboot_mybatis_demo.entity.base.Entity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("EntityDao")
public interface EntityDao {


    public int save(@Param("entity") Entity entity);

    public int saveEntities(@Param("entities") Entity[] entities);

    public int test(@Param("table") String table, @Param("id") String id);

    public int updatePropByID(@Param("entity") Entity entity, @Param("id") String id);

    public int updatePropByCondition(@Param("entity") Entity entity, @Param("condition") String condition);

    public void updateEquipmentState();

    public int deleteByID(@Param("id") String id, @Param("table") String table, @Param("primaryKey") String primaryKey);

    public int deleteByCondition(@Param("condition") String condition, @Param("table") String table);

    public int deleteEntities(@Param("ids") String[] ids, @Param("table") String table, @Param("primaryKey") String primaryKey);

    public Map<String, Object> getByID(@Param("id") String id, @Param("primaryKey") String primaryKey, @Param("table") String table);

    public Map<String, Object> getByUniqueCondition(@Param("condition") String condition, @Param("table") String table);

    public List<Map<String, Object>> getByCondition(@Param("condition") String condition, @Param("table") String table);

    public int getCountByCondition(@Param("condition") String condition, @Param("primaryKey") String primaryKey, @Param("table") String table);

    public Map<String, Object> findByID(@Param("properties") String[] properties, @Param("id") String id, @Param("primaryKey") String primaryKey, @Param("table") String table);

    public List<Map<String, Object>> findByCondition(@Param("properties") String[] properties, @Param("condition") String condition, @Param("table") String table);

    public void createTable(@Param("tableName") String tableName, @Param("fieldSqls") Set<String> fieldSqls);

    public int runSql(@Param("sql") String sql);

}
