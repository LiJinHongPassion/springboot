package com.example.li.springboot_poi_demo_v2.controller.exportExcel;

import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.example.li.springboot_poi_demo_v2.service.IDogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping()
    public String download(ModelMap modelMap) {

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

        //返回响应下载，构造http头部
        //文件内容
        modelMap.put(MapExcelConstants.MAP_LIST, list);
        modelMap.put(MapExcelConstants.ENTITY_LIST, entity);
        modelMap.put(MapExcelConstants.PARAMS, params);
        //文件名
        modelMap.put(MapExcelConstants.FILE_NAME, "EasypoiMapExcelViewTest");
        return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;

    }


    /**如果上面的方法不行,可以使用下面的用法
     * 同样的效果,只不过是直接问输出了,不经过view了
     * @param
     * @param request
     * @param response
     */

    @RequestMapping("load")
    public void downloadByPoiBaseView(ModelMap modelMap, HttpServletRequest request,
                                      HttpServletResponse response) {
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

        //返回响应下载，构造http头部
        //文件内容
        modelMap.put(MapExcelConstants.MAP_LIST, list);
        modelMap.put(MapExcelConstants.ENTITY_LIST, entity);
        modelMap.put(MapExcelConstants.PARAMS, params);
        //文件名
        modelMap.put(MapExcelConstants.FILE_NAME, "EasypoiMapExcelViewTest");
        PoiBaseView.render(modelMap, request, response, MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW);

    }


    @RequestMapping("loadDog")
    public void downloadByPoiBaseViewDog(ModelMap modelMap, HttpServletRequest request,
                                      HttpServletResponse response) {
        /*编辑表头——start*/
        List<ExcelExportEntity> entity = new ArrayList<ExcelExportEntity>();

        ExcelExportEntity excelentity ;
        entity.add(new ExcelExportEntity("狗昵称", "dog_name"));
        entity.add(new ExcelExportEntity("性别", "dog_sex"));
        entity.add(new ExcelExportEntity("年龄", "dog_age"));
        entity.add(new ExcelExportEntity("体重", "dog_height"));
        entity.add(new ExcelExportEntity("种类", "dog_kind"));
        /*编辑表头——end*/

        /*数据源-start*/
        List<Map<String, Object>> list = dogService.getDogsListMap();

        ExportParams params = new ExportParams("2412312", "测试dog", ExcelType.XSSF);
        //冻结列
        params.setFreezeCol(2);

        //返回响应下载，构造http头部
        //文件内容
        modelMap.put(MapExcelConstants.MAP_LIST, list);
        modelMap.put(MapExcelConstants.ENTITY_LIST, entity);
        modelMap.put(MapExcelConstants.PARAMS, params);
        //文件名
        modelMap.put(MapExcelConstants.FILE_NAME, "测试dog");
        PoiBaseView.render(modelMap, request, response, MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW);

    }


}
