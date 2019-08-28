## 简述

本文讲一讲easypoi的简单操作，所有涉及到的表均为单sheet，目前只实现了导出操作，后面会完善数据的导入操作

- 单表导出（实体类）
- 对象不固定的导出（Map）
- 大数据的导出

demo：https://github.com/LiJinHongPassion/springboot/tree/master/springboot_poi_demo_v2

------

## 文件目录树

springboot_poi_demo_v2
    │  Application.java（启动类）
    │  
    ├─controller
    │  ├─exportExcel（导出）
    │  │      BigExcelController.java
    │  │      MapExcelController.java
    │  │      SimpleExcelController.java
    │  │      
    │  └─importExcel（导入）
    ├─dao（模拟数据库）
    │      DogDao.java
    │      
    ├─hanlder（处理器）
    │      ExportLoadDataHandler.java
    │      
    ├─pojo（实体类）
    │      Dog.java
    │      
    └─service（服务层）
        │  IDogService.java
        │  
        └─impl
                DogServiceImpl.java

------

## 准备

#### 依赖

pom.xml

```xml
<!-- easypoi-start -->
        <dependency>
            <groupId>cn.afterturn</groupId>
            <artifactId>easypoi-base</artifactId>
            <version>4.1.0</version>
        </dependency>
        <dependency>
            <groupId>cn.afterturn</groupId>
            <artifactId>easypoi-web</artifactId>
            <version>4.1.0</version>
        </dependency>
        <dependency>
            <groupId>cn.afterturn</groupId>
            <artifactId>easypoi-annotation</artifactId>
            <version>4.1.0</version>
        </dependency>
        <!-- easypoi-end -->
```

#### **pojo**

Dog.java

```java
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * 描述：实体类
 * @author LJH
 * @date 2019/8/2-18:50
 * @QQ 1755497577
 */
public class Dog implements Serializable {

    @Excel(name = "狗昵称", orderNum = "0")
    private String dog_name;
    @Excel(name = "性别", orderNum = "1")
    private String dog_sex;
    @Excel(name = "年龄", orderNum = "2")
    private Integer dog_age;
    @Excel(name = "体重", orderNum = "3")
    private Float dog_height;
    @Excel(name = "种类", orderNum = "4")
    private String dog_kind;

    public String getDog_name() {
        return dog_name;
    }
    public void setDog_name(String dog_name) {
        this.dog_name = dog_name;
    }
    public String getDog_sex() {
        return dog_sex;
    }
    public void setDog_sex(String dog_sex) {
        this.dog_sex = dog_sex;
    }
    public Integer getDog_age() {
        return dog_age;
    }
    public void setDog_age(Integer dog_age) {
        this.dog_age = dog_age;
    }
    public Float getDog_height() {
        return dog_height;
    }
    public void setDog_height(Float dog_height) {
        this.dog_height = dog_height;
    }
    public String getDog_kind() {
        return dog_kind;
    }
    public void setDog_kind(String dog_kind) {
        this.dog_kind = dog_kind;
    }

    public Dog(String dog_name, String dog_sex, Integer dog_age, Float dog_height, String dog_kind) {
        this.dog_name = dog_name;
        this.dog_sex = dog_sex;
        this.dog_age = dog_age;
        this.dog_height = dog_height;
        this.dog_kind = dog_kind;
    }

    /**
     * 描述: 实体类对象转化为map
     *
     * @author LJH-1755497577 2019/8/2 19:53
     * @param
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     */
    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("狗昵称", getDog_name());
        map.put("性别", getDog_sex());
        map.put("年龄", getDog_age());
        map.put("体重", getDog_height());
        map.put("种类", getDog_kind());
        return map;
    }
    
    public Dog() {
    }
}
```

#### **dao**

DogDao.java

```java
/**
 * 描述：dog的数据源
 * @author LJH
 * @date 2019/8/2-19:03
 * @QQ 1755497577
 */
public class DogDao {

    public static List<Dog> dog_list = new ArrayList<>();
    public static List<Map<String, Object>> dog_list_map = new ArrayList<>();

    static {
        Dog d1 = new Dog("宝财",
                "雌性",
                (int) (Math.random()*99+1),
                (float)(Math.random()*99+51),
                "中华田园犬");
        Dog d2 = new Dog("小芳",
                "雌性",
                (int) (Math.random()*99+1),
                (float)(Math.random()*99+51),
                "贵妃犬");
        Dog d3 = new Dog("大雄",
                "雄性",
                (int) (Math.random()*99+1),
                (float)(Math.random()*99+51),
                "英国斗牛犬");
        Dog d4 = new Dog("二哈",
                "雌性",
                (int) (Math.random()*99+1),
                (float)(Math.random()*99+51),
                "拆家犬");

        dog_list.add(d1);
        dog_list.add(d2);
        dog_list.add(d3);
        dog_list.add(d4);
        
        dog_list_map.add(d1.toMap());
        dog_list_map.add(d2.toMap());
        dog_list_map.add(d3.toMap());
        dog_list_map.add(d4.toMap());
    }

    /**
     * 描述: 模拟从数据源获取List<Dog>数据
     *
     * @author LJH-1755497577 2019/8/2 19:59
     * @param
     * @return java.util.List<com.example.li.springboot_poi_demo.pojo.Dog>
     */
    public static List<Dog> getDogsList(){ return dog_list; }
    
    /**
     * 描述: 模拟从数据源获取List<Map<String, Object>>数据
     *
     * @author LJH-1755497577 2019/8/2 19:59
     * @param
     * @return java.util.List<com.example.li.springboot_poi_demo.pojo.Dog>
     */
    public static List<Map<String, Object>> getDogsListMap(){ return dog_list_map; }
}
```

#### **service**

IDogService.java

```java
/**
  * @author LJH
  * @date 2019/8/2-18:59
  * @QQ 1755497577
  */
public interface IDogService {
    
    List<Dog> getDogsList();
    List<Map<String, Object>> getDogsListMap();
}
```

DogServiceImpl.java
```java
/**
 * @author LJH
 * @date 2019/8/2-18:58
 * @QQ 1755497577
 */
@Service
public class DogServiceImpl implements IDogService {

    @Override
    public List<Dog> getDogsList() { return DogDao.getDogsList(); }

    @Override
    public List<Map<String, Object>> getDogsListMap() { return DogDao.getDogsListMap(); }
}
```

------

## 单表导出

将数据库中一张表的数据导出为excel文件，这里会用到相关注解，注解使用到实体类上，也就是这里的dog.java

注解相关介绍以及参数：ModelMaps://opensource.afterturn.cn/doc/easypoi.html#40201

#### 思路

1. 获取模拟数据源
2. 获取ExportParams对象（title, sheetName, ExcelType）
3. 构造ModelMap

#### controller

```java
import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.view.PoiBaseView

/**
 * @author LJH
 * @date 2019/8/2-23:01
 * @QQ 1755497577
 */
@Controller
@RequestMapping("/SimpleExcelController")
public class SimpleExcelController {

    @Resource
    private IDogService dogService;

    /**
     * @param map
     * @param request
     * @param response
     */
    @RequestMapping("load")
    public void downloadByPoiBaseView(ModelMap map, ModelMapServletRequest request,
                                      ModelMapServletResponse response) {
        //1. 获取模拟数据源
        List<Dog> list = dogService.getDogsList();

        //2. 获取ExportParams对象（title, sheetName, ExcelType）
        ExportParams params = new ExportParams("2412312", "测试dog", ExcelType.XSSF);
        params.setFreezeCol(2);

        //3. 构造ModelMap
        map.put(NormalExcelConstants.DATA_LIST, list);
        map.put(NormalExcelConstants.CLASS, Dog.class);
        map.put(NormalExcelConstants.PARAMS, params);
        //文件名
        map.put(MapExcelConstants.FILE_NAME, "测试dog文件名");
        PoiBaseView.render(map, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);
    }
}
```

#### 测试

url：http://localhost:8080/SimpleExcelController/load

------

## 对象不固定的导出

通常情况下我们在数据库查出来的数据格式会是`List<Map<String, Object>>`，而不是`List<Dog>`;

也有可能我们的数据比较复杂，是多个字段组成，没有对应的实体类，所以会以`List<Map<String, Object>>`存储

#### 思路

1. 编辑表头
2. 获取对应数据源
3. 构造ModelMap

#### controller

```java
import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.view.PoiBaseView;

/**
 * @author LJH
 * @date 2019/8/2-23:10
 * @QQ 1755497577
 */
@Controller
@RequestMapping("/MapExcelController")
public class MapExcelController {
    @Resource
    private IDogService dogService;

    /**
     * @param
     * @param request
     * @param response
     */
    @RequestMapping("load")
    public void downloadByPoiBaseView(ModelMap modelMap, ModelMapServletRequest request,
                                      ModelMapServletResponse response) {
        /*编辑表头，表1和表2拼成一张表——start*/
        /*表1*/
        List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();

        ExcelExportEntity excelentity = new ExcelExportEntity("姓名", "name");
        //竖着合并单元格
        excelentity.setNeedMerge(true);
        entity.add(excelentity);

        entity.add(new ExcelExportEntity("性别", "sex"));

        /*表2*/
        excelentity = new ExcelExportEntity(null, "students");
        List<ExcelExportEntity> temp = new ArrayList<ExcelExportEntity>();
        temp.add(new ExcelExportEntity("姓名", "name"));
        temp.add(new ExcelExportEntity("性别", "sex"));
        excelentity.setList(temp);
        entity.add(excelentity);
        /*编辑表头——end*/

        /*数据源-start*/
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map_table1;
        Map<String, Object> map_table2_row1;
        Map<String, Object> map_table2_row2;
        Map<String, Object> map_table2_row3;

        for (int i = 0; i < 10; i++) {
            /*表1数据源*/
            map_table1 = new HashMap<String, Object>();
            map_table1.put("name", "1" + i);
            map_table1.put("sex", "2" + i);

            /*表2数据源*/
            List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
            map_table2_row1 = new HashMap<String, Object>();
            map_table2_row1.put("name", "stu1" + i);
            map_table2_row1.put("sex", "stu2" + i);

            map_table2_row2 = new HashMap<String, Object>();
            map_table2_row2.put("name", "stu11" + i);
            map_table2_row2.put("sex", "stu22" + i);

            map_table2_row3 = new HashMap<String, Object>();
            map_table2_row3.put("name", "stu111" + i);
            map_table2_row3.put("sex", "stu222" + i);

            tempList.add(map_table2_row1);
            tempList.add(map_table2_row2);
            tempList.add(map_table2_row3);

            /*将表2数据合并到表1*/
            map_table1.put("students", tempList);

            /*总数据源--一行*/
            list.add(map_table1);
        }

        ExportParams params = new ExportParams("2412312", "测试复杂", ExcelType.XSSF);
        //冻结列
        params.setFreezeCol(2);

        //返回响应下载，构造ModelMap
        //文件内容
        modelMap.put(MapExcelConstants.MAP_LIST, list);
        modelMap.put(MapExcelConstants.ENTITY_LIST, entity);
        modelMap.put(MapExcelConstants.PARAMS, params);
        //文件名
        modelMap.put(MapExcelConstants.FILE_NAME, "EasypoiMapExcelViewTest");
        PoiBaseView.render(modelMap, request, response, MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW);
    }


    @RequestMapping("loadDog")
    public void downloadByPoiBaseViewDog(ModelMap modelMap, ModelMapServletRequest request,
                                      ModelMapServletResponse response) {
        /*1. 编辑表头——start*/
        List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();
        ExcelExportEntity excelentity ;
        entity.add(new ExcelExportEntity("狗昵称", "dog_name"));
        entity.add(new ExcelExportEntity("性别", "dog_sex"));
        entity.add(new ExcelExportEntity("年龄", "dog_age"));
        entity.add(new ExcelExportEntity("体重", "dog_height"));
        entity.add(new ExcelExportEntity("种类", "dog_kind"));
        /*编辑表头——end*/

        /*2. 数据源-start*/
        List<Map<String, Object>> list = dogService.getDogsListMap();

        ExportParams params = new ExportParams("2412312", "测试dog", ExcelType.XSSF);
        //冻结列
        params.setFreezeCol(2);

        //3. 返回响应下载，构造ModelMap
        //文件内容
        modelMap.put(MapExcelConstants.MAP_LIST, list);
        modelMap.put(MapExcelConstants.ENTITY_LIST, entity);
        modelMap.put(MapExcelConstants.PARAMS, params);
        //文件名
        modelMap.put(MapExcelConstants.FILE_NAME, "测试dog");
        PoiBaseView.render(modelMap, request, response, MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW);
    }
}

```

#### 测试

url-1：http://localhost:8080/MapExcelController/loadDog

url-2：http://localhost:8080/MapExcelController/load

------

## 大数据的导出

解决导出数据量大,批量查询批量插入方式,防止内存溢出

大数据导出是当我们的导出数量在几万,到上百万的数据时,一次从数据库查询这么多数据加载到内存然后写入会对我们的内存和CPU都产生压力,这个时候需要我们像分页一样处理导出分段写入Excel缓解Excel的压力，EasyPoi提供的是两个方法 ***强制使用 xssf版本的Excel ***

#### 思路

1. 实现IExcelExportServer接口，编写处理器
2. 获取ExportParams对象（title, sheetName, ExcelType）
3. 构造ModelMap，这里需注意

```java
/*就是我们的查询参数,会带到接口中,供接口查询使用（例如rows,page等）*/
map.put(BigExcelConstants.DATA_PARAMS, new HashMap<String,String>());
```

#### hanlder

```java
import cn.afterturn.easypoi.handler.inter.IExcelExportServer;

@Component
public class ExportLoadDataHandler implements IExcelExportServer {

    /**
     * 描述:
     *
     * @author LJH-1755497577 2019/8/3 0:58
     * @param obj 可以传参数，例如数据库每页查询多少
     * @param page 循环查询次数
     * @return java.util.List<java.lang.Object>
     */
    @Override
    public List<Object> selectListForExcelExport(Object obj, int page) {
        //获取数据源
        List<Object> list = new ArrayList<Object>();
        List<Dog> dogsList = DogDao.getDogsList();

        for ( Dog dog : dogsList) {
            list.add(dog);
        }

        if(page>10000){
            return null;
        }
        return list;
    }
}
```

#### controller

```java
import cn.afterturn.easypoi.entity.vo.BigExcelConstants;
import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.handler.inter.IExcelExportServer;
import cn.afterturn.easypoi.view.PoiBaseView;

/**
 * 针对导出数据量大,批量查询批量插入方式,防止内存溢出解决的
 * 大数据导出是当我们的导出数量在几万,到上百万的数据时,
 * 一次从数据库查询这么多数据加载到内存然后写入会对我们的内存和CPU都产生压力,
 * 这个时候需要我们像分页一样处理导出分段写入Excel缓解Excel的压力
 * EasyPoi提供的是两个方法 ***强制使用 xssf版本的Excel ***
 */
@Controller
@RequestMapping("/BigExcelController")
public class BigExcelController {

    /*需要自己写处理器*/
    @Autowired
    private IExcelExportServer excelExportServer;

    /**
     * @param map
     * @param request
     * @param response
     */
    @RequestMapping("load")
    public void downloadByPoiBaseView(ModelMap map, ModelMapServletRequest request,
                                      ModelMapServletResponse response) {
        //1. 获取ExportParams对象（title, sheetName, ExcelType）
        ExportParams params = new ExportParams("2412312", "测试", ExcelType.XSSF);
        params.setFreezeCol(2);
        //2. 构造ModelMap
        map.put(BigExcelConstants.CLASS, Dog.class);
        map.put(BigExcelConstants.PARAMS, params);

        //就是我们的查询参数,会带到接口中,供接口查询使用（例如rows）
        map.put(BigExcelConstants.DATA_PARAMS, new HashMap<String,String>());

        map.put(BigExcelConstants.DATA_INTER,excelExportServer);
        //文件名
        map.put(MapExcelConstants.FILE_NAME, "测试dog");
        PoiBaseView.render(map, request, response, BigExcelConstants.EASYPOI_BIG_EXCEL_VIEW);
    }
}
```

#### 测试

url：http://localhost:8080/BigExcelController/load

------

## 参考资料

- https://gitee.com/lemur/easypoi （easypoi的gitee地址）
- https://gitee.com/lemur/easypoi-test  （easypoi小demo的gitee地址）
- https://opensource.afterturn.cn/doc/easypoi.html  （easypoi文档地址）
- https://opensource.afterturn.cn/doc/easypoi.html#106 官方文档具体实践代码