package com.example.exceloperatedemo.controller;

import com.example.exceloperatedemo.service.TestService;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.6.11
 */
@Controller
public class TestController {

    TestService service = new TestService();

    /**
     * 导出excel
     * @param request
     * @param response
     * @param customName
     */
    @RequestMapping("export")
    public void export(HttpServletRequest request, HttpServletResponse response, String customName) {
        service.export(request, response, customName);
    }

    /**
     * 导出excel
     */
    @RequestMapping("import")
    @ResponseBody
    public String importData(MultipartFile file) {
        return service.importData(file, 4);
    }


}
