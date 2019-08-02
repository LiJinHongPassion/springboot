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
