package com.example.li.springboot_poi_demo.service.Impl;

import com.example.li.springboot_poi_demo.model.PersonExportVo;
import com.example.li.springboot_poi_demo.service.IExcelService;
import com.example.li.springboot_poi_demo.utils.ExcelUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LJH
 * @date 2019/8/7-11:27
 * @QQ 1755497577
 */
@Service
public class ExcelServiceImpl implements IExcelService {
    /**
     * 导出 本地图片/网络图片导出，数据导出
     *
     * @param response
     */
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<PersonExportVo> personList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PersonExportVo personVo = new PersonExportVo();
            personVo.setName("张三" + i);
            personVo.setUsername("张三" + i);
            personVo.setPhoneNumber("18888888888");
            //本地图片
            personVo.setImageUrl("/static/user1-128x128.jpg");
            personList.add(personVo);
        }
        {
            PersonExportVo personVo = new PersonExportVo();
            personVo.setName("张三");
            personVo.setUsername("张三");
            personVo.setPhoneNumber("18888888888");
            //网络图片
            personVo.setImageUrl("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=108228188,2741176027&fm=27&gp=0.jpg");
            personList.add(personVo);
        }
        {
            PersonExportVo personVo = new PersonExportVo();
            personVo.setName("张三");
            personVo.setUsername("张三");
            personVo.setPhoneNumber("18888888888");
            //网络图片
            personVo.setImageUrl("https://foter.com/photos/394/anime-pet-kitty.jpg");
            personList.add(personVo);
        }


        ExcelUtils.exportExcel(personList, "员工信息表", "员工信息", PersonExportVo.class, "员工信息", response);
    }

    /**
     * 导入
     *
     * @param file
     */
    public Object importExcel(MultipartFile file) throws IOException {
        List<PersonExportVo> personVoList = ExcelUtils.importExcel(file, PersonExportVo.class);
        return personVoList;
    }


    /**
     * 上传文件到fastdfs
     * @param file 上传图片文件
     * @return 返回图片路径
     * @throws IOException
     */
    public String uploadFile(File file) throws IOException {
//        String path = excelService.uploadFile(file);
//        return path;
        /*此处上传文件到fastdfs代码省略*/
        return "";
    }
}
