package com.example.li.springboot_mybatis_demo.dao.base;

import com.example.li.springboot_mybatis_demo.entity.base.Entity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service("Dao")
public class Dao {

    @Resource(name = "EntityDao")
    EntityDao entityDao;


    @Resource(name = "SearchDao")
    SearchDao searchDao;

    // EntityDao
    public int save(Entity entity) {
        return entityDao.save(entity);
    }

    public int saveEntities(Entity[] entitys) {
        return entityDao.saveEntities(entitys);
    }

    public int updatePropByCondition(Entity entity, String condition) {
        return entityDao.updatePropByCondition(entity, condition);
    }

    public int updatePropByID(Entity entity, String ID) {
        return entityDao.updatePropByID(entity, ID);
    }

    public void updateEquipmentState() {
        entityDao.updateEquipmentState();
    }

    public <T extends Entity> int deleteByID(String ID, Class<T> t) {
        return entityDao.deleteByID(ID, Entity.getTableName(t), Entity.getPrimaryKey(t));
    }

    public <T extends Entity> int deleteByCondition(String condition, Class<T> t) {
        return entityDao.deleteByCondition(condition, Entity.getTableName(t));
    }

    public <T extends Entity> int deleteEntities(String[] IDs, Class<T> t) {
        return entityDao.deleteEntities(IDs, Entity.getTableName(t), Entity
                .getPrimaryKey(t));
    }

    public <T extends Entity> T getByID(String ID, Class<T> t) {
        Map<String, Object> properties = entityDao.getByID(ID, Entity.getPrimaryKey(t), Entity.getTableName(t));
        T entity = null;
        if (properties == null) return null;
        try {
            entity = t.newInstance();
            entity.setProperties(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    public <T extends Entity> T getByUniqueCondition(String condition, Class<T> t) {
        Map<String, Object> properties = entityDao.getByUniqueCondition(condition, Entity.getTableName(t));
        T entity = null;
        if (properties == null) return null;

        try {
            entity = t.newInstance();
            entity.setProperties(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }

    public <T extends Entity> List<T> getByCondition(String condition, Class<T> t) {
        List<Map<String, Object>> list = entityDao.getByCondition(condition, Entity.getTableName(t));

        List<T> entityList = new ArrayList<T>();
        for (Map<String, Object> properties : list) {
            T entity = null;
            try {
                entity = t.newInstance();
            } catch (Exception e) {

                e.printStackTrace();
            }
            entity.setProperties(properties);
            entityList.add(entity);
        }
        return entityList;
    }

    public <T extends Entity> Map<String, Object> findByID(String[] properties, String id, Class<T> t) {
        return entityDao.findByID(properties, id, Entity.getPrimaryKey(t), Entity.getTableName(t));
    }

    public <T extends Entity> List<Map<String, Object>> findByCondition(String[] properties, String condition, Class<T> t) {
        return entityDao.findByCondition(properties, condition, Entity.getTableName(t));
    }

    public void createTable(String tableName, Set<String> fieldSqls) {
        entityDao.createTable(tableName, fieldSqls);
    }

    ;


    public int runSql(String sql) {
        if (sql != null && !sql.equals("")) {
            return entityDao.runSql(sql);
        } else {
            System.out.println(" sql is null");
            return 0;
        }
    }

    // SearchDao
    public List<Map<String, Object>> searchForeign(String[] properties, String baseEntity, String joinEntity, String[] foreignEntities, String condition) {
        List<Map<String, Object>> result;
        result = searchDao.searchForeign(properties, baseEntity, joinEntity, foreignEntities, null, condition);
        return result;
    }

    public int getForeignCount(String primaryKey, String baseEntity, String joinEntity, String[] foreignEntity, String condition) {
        return searchDao.getForeignCount(primaryKey, baseEntity, joinEntity, foreignEntity, null, condition);
    }

    public List<Map<String, Object>> searchWithPage(String[] properties, String baseEntity, String joinEntity,
                                                    String[] foreignEntities, String condition, String groupField,
                                                    String orderField, String sortMethod, int pageNum, int pageIndex) {

        int startIndex = pageNum * (pageIndex - 1);
        List<Map<String, Object>> result;

        if (orderField == null || orderField.equals("")) {
            try {
                if (baseEntity != null && !baseEntity.equals("")) {
                    String className = "ssm.entity." + baseEntity + "." + baseEntity.toUpperCase().charAt(0) + baseEntity.substring(1, baseEntity.length());
                    //	System.out.println(className);
                    Entity entity = (Entity) Class.forName(className).newInstance();
                    orderField = entity.getPrimaryKey();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //orderField = TableCreator.getPrimaryKey(baseEntity);
        }
        if (sortMethod == null || sortMethod.equals("")) {
            sortMethod = "desc"; //asc是指定列按升序排列，desc则是指定列按降序排列
        }

        result = searchDao.searchWithPageInMysql(properties, baseEntity,
                joinEntity, foreignEntities, null, condition, groupField,
                orderField, sortMethod, startIndex, pageNum);
        return result;
    }
}
