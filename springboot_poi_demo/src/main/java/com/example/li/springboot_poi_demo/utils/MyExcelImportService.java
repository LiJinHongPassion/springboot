package com.example.li.springboot_poi_demo.utils;


import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelImportEntity;
import cn.afterturn.easypoi.excel.imports.ExcelImportService;
import cn.afterturn.easypoi.util.PoiPublicUtil;
import com.example.li.springboot_poi_demo.service.IExcelService;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.Map;

/**Excel 导入服务
 * @author LJH
 * @date 2019/8/7-10:53
 * @QQ 1755497577
 */
@SuppressWarnings({"rawtypes", "unchecked", "hiding"})
public class MyExcelImportService extends ExcelImportService {

    @Resource
    private IExcelService excelService;

    /**
     * @param object
     * @param picId
     * @param excelParams
     * @param titleString
     * @param pictures
     * @param params
     * @throws Exception
     */
    private void saveImage(Object object, String picId, Map<String, ExcelImportEntity> excelParams,
                           String titleString, Map<String, PictureData> pictures,
                           ImportParams params) throws Exception {
        if (pictures == null) {
            return;
        }
        PictureData image = pictures.get(picId);
        if (image == null) {
            return;
        }
        byte[] data     = image.getData();
        String fileName = "pic" + Math.round(Math.random() * 100000000000L);
        fileName += "." + PoiPublicUtil.getFileExtendName(data);
        if (excelParams.get(titleString).getSaveType() == 1) {


            File f = new File("d://");
            if (!f.exists()) {
                f.mkdirs();
            }

            FileOutputStream fos = new FileOutputStream("d://" + fileName);
            fos.write(data);
            fos.close();

            String path = excelService.uploadFile(f);

            setValues(excelParams.get(titleString), object, path);
        } else {
            setValues(excelParams.get(titleString), object, data);
        }
    }


}