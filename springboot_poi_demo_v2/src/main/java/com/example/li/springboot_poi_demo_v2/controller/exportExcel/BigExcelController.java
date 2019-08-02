package com.example.li.springboot_poi_demo_v2.controller.exportExcel;

import cn.afterturn.easypoi.entity.vo.BigExcelConstants;
import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.handler.inter.IExcelExportServer;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.example.li.springboot_poi_demo_v2.pojo.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

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


    @RequestMapping()
    public String download(ModelMap map) {
        ExportParams params = new ExportParams("2412312", "测试", ExcelType.XSSF);
        params.setFreezeCol(2);
        map.put(BigExcelConstants.CLASS, Dog.class);
        map.put(BigExcelConstants.PARAMS, params);

        //就是我们的查询参数,会带到接口中,供接口查询使用（例如rows）
        map.put(BigExcelConstants.DATA_PARAMS, new HashMap<String,String>());

        map.put(BigExcelConstants.DATA_INTER,excelExportServer);
        //文件名
        map.put(MapExcelConstants.FILE_NAME, "测试dog");
        return BigExcelConstants.EASYPOI_BIG_EXCEL_VIEW;

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
        ExportParams params = new ExportParams("2412312", "测试", ExcelType.XSSF);
        params.setFreezeCol(2);
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