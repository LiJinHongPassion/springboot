package com.example.li.springboot_poi_demo_v2.controller.importExcel;

import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.imports.ExcelImportService;
import cn.afterturn.easypoi.exception.excel.ExcelImportException;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.example.li.springboot_poi_demo_v2.pojo.Dog;
import com.example.li.springboot_poi_demo_v2.service.IDogService;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @author LJH
 * @date 2019/8/2-23:01
 * @QQ 1755497577
 */
@Controller
@RequestMapping("/SimpleExcelImportController")
public class SimpleExcelImportController {

    private static Logger logger = LoggerFactory.getLogger(SimpleExcelImportController.class);

    /**
     * 描述: 解析上传的xls文件
     *
     * @author LJH-1755497577 2019/8/2 23:23
     * @param file
     * @return java.util.List<com.example.li.springboot_poi_demo.pojo.Dog>
     */
    @PostMapping("/upload")
    public List<Dog> importExcel( @RequestParam("file") MultipartFile file){
        //导入参数的配置可以参照springboot_poi_demo中的util下的easypoiutil.java
        ImportParams params = new ImportParams();
        params.setTitleRows(1);
        params.setHeadRows(1);

         /**
         * 注意: Dog.class里面必须有个无参构造函数，否则无法反射--`newInstance()会报错`
         */
        List<Dog> dogList = null;
        try {
            dogList = importExcel(file.getInputStream(), Dog.class, params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dogList;
    }


    /****************以下内容可以封装于统一的工具类*******************/


    /**
     * Excel 导入 数据源本地文件,不返回校验结果 导入 字 段类型 Integer,Long,Double,Date,String,Boolean
     *
     * @param file
     * @param pojoClass
     * @param params
     * @return
     */
    public static <T> List<T> importExcel(File file, Class<?> pojoClass, ImportParams params) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            return new ExcelImportService().importExcelByIs(in, pojoClass, params, false).getList();
        } catch (ExcelImportException e) {
            throw new ExcelImportException(e.getType(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ExcelImportException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    /**
     * Excel 导入 数据源IO流,不返回校验结果 导入 字段类型 Integer,Long,Double,Date,String,Boolean
     *
     * @param inputstream
     * @param pojoClass
     * @param params
     * @return
     * @throws Exception
     */
    public static <T> List<T> importExcel(InputStream inputstream, Class<?> pojoClass,
                                          ImportParams params) throws Exception {
        return new ExcelImportService().importExcelByIs(inputstream, pojoClass, params, false).getList();
    }
}
