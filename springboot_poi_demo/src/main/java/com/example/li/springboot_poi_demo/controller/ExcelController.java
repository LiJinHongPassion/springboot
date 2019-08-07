package com.example.li.springboot_poi_demo.controller;

import com.example.li.springboot_poi_demo.model.PersonExportVo;
import com.example.li.springboot_poi_demo.service.IExcelService;
import com.example.li.springboot_poi_demo.utils.ExcelUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ljh
 * @date 2019/8/2
 */
@RestController
@RequestMapping("excel")
public class ExcelController {

    @Resource
    private IExcelService excelService;

    /**
     * 导出 本地图片/网络图片导出，数据导出
     *
     * @param response
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportExcel(HttpServletResponse response) throws IOException {
        excelService.exportExcel(response);
    }

    /**
     * 导入
     *
     * @param file
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public Object importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        return excelService.importExcel(file);
    }

}
