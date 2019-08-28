## 简述

场景需求：在公司日常运营中，需要对网站中相关信息进行采集保存，例如财政信息、用户信息、库存信息等需要做报表，就需要将数据写入excel文件里

解决方案：Apache poi -- Apache的poi功能十分强大，但是因为强大，所以操作的粒度更细，需要编写的代码更多；

easypoi -- easypoi是基于Apache poi二次开发的一个工具，日常生活中我们所需要对文档的操作也就是导入和导出功能，easypoi能够简化开发过程，使用起来更加方便，完成文件的导入，导出也就4，5行代码；

demo：https://github.com/LiJinHongPassion/springboot/tree/master/springboot_poi_demo

本文只对`excel`文档操作（没有使用模板），对于`world`文档的操作这里不做说明

**思路**

导出：controller层获取请求
--->service层调用dao层接口
--->dao层从数据库获取数据（格式`List<?>`或者`List<Map<String, Object>>`）
--->返回service层
--->返回controller层
--->调用EasyPoiUtil工具类里面的方法

导入：controller层获取请求
--->获取导入文件数据
--->封装为实体类，返回List<实体类>
--->下一步操作（可持久化，数据分析等操作，这里不演示）

------

## 项目目录

![](./images/7.png)

| 类名                | 描述                                                         |
| ------------------- | ------------------------------------------------------------ |
| DogController.java  | 包含2个方法<br />- downLoadDog(HttpServletResponse response) <br />调用exportExcel，数据源格式是List<Dog><br /><br />- importExcel( @RequestParam("file") MultipartFile file) <br />解析上传的xls文件 |
| DogDao.java         | 模拟数据源，包含1个方法<br />- getDogsList()<br />模拟从数据源获取List<Dog>数据<br /> |
| Dog.java            | 实体类，实例化后的对象可以理解为excel文件里面的一行数据，运用了@Excel注解 |
| DogServiceImpl.java | 接口实现类                                                   |
| IDogService         | 接口                                                         |
| EasyPoiUtil.java    | 工具类，重要，方法具体含义请参照代码查看                     |
| Application.java    | 项目启动类                                                   |
| upload.html         | 文件上传页面                                                 |

小白学习方式：

1. 先根据下面的教程将项目搭建起来，并能执行成功
2. 查看工具类
3. 查看controller层


## 依赖

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

## 准备

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
public class dogDao {

    public static List<Dog> dog_list = new ArrayList<>();

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
    }

    /**
     * 描述: 模拟从数据源获取List<Dog>数据
     *
     * @author LJH-1755497577 2019/8/2 19:59
     * @param
     * @return java.util.List<com.example.li.springboot_poi_demo.pojo.Dog>
     */
    public static List<Dog> getDogsList(){ return dog_list; }

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
    public List<Dog> getDogsList() {
        return DogDao.getDogsList();
    }

    @Override
    public List<Map<String, Object>> getDogsListMap() {
        return DogDao.getDogsListMap();
    }
}

```

## EasyPoiUtil.java

- `exportExcel`为导出方法
- `importExcel`为导入方法

```java
package com.example.li.springboot_poi_demo.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther: ljh
 * @Description: Excle 文件导入导出Util(easypoi)
 */
public class EasyPoiUtil {
   /**
    * 功能描述：复杂导出Excel，包括文件名以及表名。创建表头
    *
    * @author ljh
    * @param list 导出的实体类
    * @param title 表头名称
    * @param sheetName sheet表名
    * @param pojoClass 映射的实体类
    * @param isCreateHeader 是否创建表头
    * @param fileName
    * @param response
    * @return 
   */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }
 
 
    /**
     * 功能描述：复杂导出Excel，包括文件名以及表名,不创建表头
     *
     * @author ljh
     * @param list 导出的实体类
     * @param title 表头名称
     * @param sheetName sheet表名
     * @param pojoClass 映射的实体类
     * @param fileName
     * @param response
     * @return
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }
 
    /**
     * 功能描述：Map 集合导出
     *
     * @author ljh
     * @param list 实体集合
     * @param fileName 导出的文件名称
     * @param response
     * @return
    */
    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        defaultExport(list, fileName, response);
    }
 
    /**
     * 功能描述：默认导出方法
     *
     * @author ljh
     * @param list 导出的实体集合
     * @param fileName 导出的文件名
     * @param pojoClass pojo实体
     * @param exportParams ExportParams封装实体
     * @param response
     * @return
     */
    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }
 
    /**
     * 功能描述：Excel导出
     *
     * @author ljh
     * @param fileName 文件名称
     * @param response
     * @param workbook Excel对象
     * @return
    */
    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new  RuntimeException(e);
        }
    }
 
    /**
     * 功能描述：默认导出方法
     *
     * @author ljh
     * @param list 导出的实体集合
     * @param fileName 导出的文件名
     * @param response
     * @return
    */
    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) ;
        downLoadExcel(fileName, response, workbook);
    }
    
    
    /**
     * 功能描述：根据文件路径来导入Excel
     *
     * @author ljh
     * @param filePath 文件路径
     * @param titleRows 表标题的行数
     * @param headerRows 表头行数
     * @param pojoClass Excel实体类
     * @return
    */
    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        //判断文件是否存在
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();
 
        }
        return list;
    }
 
    /**
     * 功能描述：根据接收的Excel文件来导入Excel,并封装成实体类
     *
     * @author ljh
     * @param file 上传的文件
     * @param titleRows 表标题的行数
     * @param headerRows 表头行数
     * @param pojoClass Excel实体类
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            throw new RuntimeException("excel文件不能为空");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
 
        }
        return list;
    }
 
}
```

## Controller

```java
package com.example.li.springboot_poi_demo.controller;

import com.example.li.springboot_poi_demo.pojo.Dog;
import com.example.li.springboot_poi_demo.service.IDogService;
import com.example.li.springboot_poi_demo.util.EasyPoiUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author LJH
 * @date 2019/8/2-19:26
 * @QQ 1755497577
 */

@RestController
@RequestMapping("/dog")
public class DogController {

    @Resource
    private IDogService dogService;

    /**
     * 描述: 调用exportExcel，数据源格式是List<Dog>
     *
     * @author LJH-1755497577 2019/8/2 19:49
     * @param response
     * @return void 返回文件下载
     */
    @GetMapping("/list")
    public void downLoadDog(HttpServletResponse response) {

        //数据源
        List<Dog> dogList = dogService.getDogsList();

        EasyPoiUtil.exportExcel(dogList, "dog title", "dog home sheetname", Dog.class, "dogs filename.xls", response);

    }

    /**
     * 描述: 解析上传的xls文件
     *
     * @author LJH-1755497577 2019/8/2 20:23
     * @param file
     * @return java.util.List<com.example.li.springboot_poi_demo.pojo.Dog>
     */
    @PostMapping("/upload")
    public List<Dog> importExcel( @RequestParam("file") MultipartFile file){

        /**
         * 注意: Dog.class里面必须有个无参构造函数，否则无法反射--`newInstance()会报错`
         */
        List<Dog> dogList = EasyPoiUtil.importExcel(file, 1, 1, Dog.class);

        return dogList;
    }

}
```



## 测试

#### 导出

```yml
url: http://localhost:8080/dog/list

api方法: public void downLoadDog(HttpServletResponse response)

EasyPoiUtil的方法: public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response)
```

1. 游览器输入url：http://localhost:8080/dog/listl，会下载此文件

![](./images/1.png)

2. 打开下载的xls文件

![](./images/2.png)

------

#### 导入

在`easypoiutil.java`中提供了导入方式
1. 游览器输入url：http://localhost:8080/upload.html，点击上传文件（示例文件在demo中已经给出）

![](./images/5.png)

2. 上传成功后返回的数据

![](./images/6.png)

------

## 参考资料

- https://gitee.com/lemur/easypoi （easypoi的gitee地址）
- https://gitee.com/lemur/easypoi-test  （easypoi小demo的gitee地址）
- https://opensource.afterturn.cn/doc/easypoi.html  （easypoi文档地址）