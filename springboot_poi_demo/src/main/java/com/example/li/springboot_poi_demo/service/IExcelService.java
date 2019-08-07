package com.example.li.springboot_poi_demo.service;


import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @author LJH
 * @date 2019/8/7-11:29
 * @QQ 1755497577
 */
public interface IExcelService {

    /**
     * 导出 本地图片/网络图片导出，数据导出
     *
     * @param response
     */
    void exportExcel(HttpServletResponse response) throws IOException;

    /**
     * 导入
     *
     * @param file
     */
    Object importExcel(MultipartFile file) throws IOException ;

    /**
     * 上传文件到fastdfs
     * @param file 上传图片文件
     * @return 返回图片路径
     * @throws IOException
     */
    String uploadFile(File file) throws IOException ;
}

