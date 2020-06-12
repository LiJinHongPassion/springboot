package com.example.exceloperatedemo.service;

import com.example.exceloperatedemo.entity.Person;
import com.example.exceloperatedemo.util.ExcelTemplateUtil;
import com.example.exceloperatedemo.util.ReadExcelUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.6.11
 */
@Service
public class TestService {

    public static List<Person> personList = new ArrayList<>();
    static {
        personList.add(new Person(1, "测试", "没有简介", 23, "男", new Date()));
        personList.add(new Person(2, "测试2", "没有简介2", 23, "男", new Date()));
    }

    /**
     * 导出excel
     * @param request
     * @param response
     * @param customName
     */
    public void export(HttpServletRequest request, HttpServletResponse response, String customName) {

        //1. 构造数据源
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String createDate = sdf.format(new Date());

        Map<String, Object> map = new HashMap<>();
        map.put("personList", personList);
        map.put("customName", customName);
        map.put("operatorName", "CodeAnt");
        map.put("createDate", createDate);

        //2. 加载模板
        String path = "classpath:static" + File.separator + "xls"+ File.separator +"学生信息汇总表.xls";
        try {
            //2.1 加载模板文件到项目
            File file = ResourceUtils.getFile(path);
            //2.2 获取模板的真实路径, 获取出来的路径类似于"C:\\Users\\Administrator\\Desktop\\excel-operate-demo\\target\\classes\\static\\xls\\学生信息汇总表.xls";
            path = file.getPath();
        } catch (FileNotFoundException e) {
            System.out.println(".xls模板文件找不到");
        }

        //3. 填充数据并导出文件
        try {
            ExcelTemplateUtil.parse(path, "学生信息汇总表", map, response);
        } catch (Exception e) {
            System.out.println("导出excel异常, 模板文件路径:" + path);
        }
    }


    /**
     * 导入excel
     * @param file
     * @param filterRowNum 读取文件的几列,例如文件一共10列,只读前5列,这里就填5
     * @return
     */
    public String importData(MultipartFile file, Integer filterRowNum){
        List<String[]> result;
        try {
            result = ReadExcelUtils.readExcel(file, filterRowNum);
        } catch (IOException e) {
            System.out.println("文件读取异常");
            return "文件读取异常";
        }

        StringBuffer stringBuffer = new StringBuffer();

        result.forEach(s->{
            for (int i = 0; i < s.length; i ++){
                stringBuffer.append(s[i]).append("\t");
            }
            stringBuffer.append("\n");
        });

        return stringBuffer.toString();
    }
}

