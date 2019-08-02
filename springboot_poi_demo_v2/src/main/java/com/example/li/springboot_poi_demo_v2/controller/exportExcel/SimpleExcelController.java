package com.example.li.springboot_poi_demo_v2.controller.exportExcel;

import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.example.li.springboot_poi_demo_v2.pojo.Dog;
import com.example.li.springboot_poi_demo_v2.service.IDogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    @RequestMapping()
    public String download(ModelMap map) {

        //模拟数据源
        List<Dog> list = dogService.getDogsList();

        ExportParams params = new ExportParams("2412312", "测试dog", ExcelType.XSSF);
        //冻结列
        params.setFreezeCol(2);

        //返回响应下载，构造http头部
        map.put(NormalExcelConstants.DATA_LIST, list);
        map.put(NormalExcelConstants.CLASS, Dog.class);
        map.put(NormalExcelConstants.PARAMS, params);
        //文件名
        map.put(MapExcelConstants.FILE_NAME, "测试dog文件名");
        return NormalExcelConstants.EASYPOI_EXCEL_VIEW;

    }

    /**如果上面的方法不行,可以使用下面的用法
     * 同样的效果,只不过是直接问输出了,不经过view了
     * @param map
     * @param request
     * @param response
     */

    @RequestMapping("load")
    public void downloadByPoiBaseView(ModelMap map, HttpServletRequest request,
                                      HttpServletResponse response) {
        //模拟数据源
        List<Dog> list = dogService.getDogsList();

        ExportParams params = new ExportParams("2412312", "测试dog", ExcelType.XSSF);
        params.setFreezeCol(2);

        //返回响应下载，构造http头部
        map.put(NormalExcelConstants.DATA_LIST, list);
        map.put(NormalExcelConstants.CLASS, Dog.class);
        map.put(NormalExcelConstants.PARAMS, params);
        //文件名
        map.put(MapExcelConstants.FILE_NAME, "测试dog文件名");
        PoiBaseView.render(map, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);

    }

}
