package com.example.exceloperatedemo.util;

import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel工具类，通过excel模板文件自动生成excel文件
 *
 */
public class ExcelTemplateUtil {


    public static void parseMultiple(String templatePath,
                                     String fileName,
                                     List<Map<String, Object>> models,
                                     List<String> sheetNames,
                                     String beanName ,
                                     HttpServletResponse response) throws Exception {
        try (FileInputStream inputStream = new FileInputStream(templatePath)) {
            //将数据渲染到excel模板上
           // Workbook workbook = new XLSTransformer().transformXLS(inputStream, model);

            Workbook workbook = new XLSTransformer().transformMultipleSheetsList(
                    inputStream,models,sheetNames,beanName,new HashMap(),0);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            //设置response头信息
            response.setContentType(String.format("%s;charset=utf-8", "application/x"));
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    new String((fileName + ".xlsx").getBytes("utf-8"), "iso8859-1"));
            response.setHeader("Content-Length", String.valueOf(out.toByteArray().length));
            response.getOutputStream().write(out.toByteArray());
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     *  .xlsx文件模板导出
     *
     * @param templatePath excel模板文件位置
     * @param fileName 导出excel的文件名
     * @param model 数据
     * @param response 请求进来的response,通过改写header,直接返回文件流进行下载
     * @throws Exception
     */
    public static void parseX(String templatePath, String fileName, Map<String, Object> model, HttpServletResponse response) throws Exception {
        try (FileInputStream inputStream = new FileInputStream(templatePath)) {
            //将数据渲染到excel模板上
            Workbook workbook = new XLSTransformer().transformXLS(inputStream, model);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            //设置response头信息
            response.setContentType(String.format("%s;charset=utf-8", "application/x"));
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    new String((fileName + ".xlsx").getBytes("utf-8"), "iso8859-1"));
            response.setHeader("Content-Length", String.valueOf(out.toByteArray().length));
            response.getOutputStream().write(out.toByteArray());
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }


    /**
     *  .xls文件模板导出
     *
     * @param templatePath excel模板文件位置
     * @param fileName 导出excel的文件名
     * @param model 数据
     * @param response 请求进来的response,通过改写header,直接返回文件流进行下载
     * @throws Exception
     */
    public static void parse(String templatePath, String fileName, Map<String, Object> model, HttpServletResponse response) throws Exception {
        try (FileInputStream inputStream = new FileInputStream(templatePath)) {
            //将数据渲染到excel模板上
            Workbook workbook = new XLSTransformer().transformXLS(inputStream, model);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            //设置response头信息
            response.setContentType(String.format("%s;charset=utf-8", "application/x"));
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    new String((fileName + ".xls").getBytes("utf-8"), "iso8859-1"));
            response.setHeader("Content-Length", String.valueOf(out.toByteArray().length));
            response.getOutputStream().write(out.toByteArray());
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     *
     * @param templatePath excel模板文件位置
     * @param fileName 导出excel的文件名
     * @param model 数据
     * @param response 请求进来的response,通过改写header,直接返回文件流进行下载
     * @throws Exception
     * @param cellRangeAddressList
     */
    public static void parse(String templatePath,
                             String fileName,
                             Map<String, Object> model,
                             HttpServletResponse response,
                             Collection<CellRangeAddress> cellRangeAddressList
                             ) throws Exception {
        try (FileInputStream inputStream = new FileInputStream(templatePath)) {
            //将数据渲染到excel模板上
            Workbook workbook = new XLSTransformer().transformXLS(inputStream, model);
            Sheet sheet = workbook.getSheetAt(0);
            for( CellRangeAddress cellRangeAddress : cellRangeAddressList ){
                downMergedRegion(cellRangeAddress.getFirstRow(),cellRangeAddress.getFirstColumn(),
                        cellRangeAddress.getLastRow(),cellRangeAddress.getLastColumn(),sheet);
                sheet.addMergedRegion(cellRangeAddress);
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            //设置response头信息
            response.setContentType(String.format("%s;charset=utf-8", "application/x"));
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    new String((fileName + ".xls").getBytes("utf-8"), "iso8859-1"));
            response.setHeader("Content-Length", String.valueOf(out.toByteArray().length));
            response.getOutputStream().write(out.toByteArray());
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public static void parseByMergeModel(String templatePath,
            String fileName,
            Map<String, Object> model,
            HttpServletResponse response,
            Collection<CellRangeAddress> cellRangeAddressList,
            List<String> valueList
            )throws Exception {
    	try (FileInputStream inputStream = new FileInputStream(templatePath)) {
            //将数据渲染到excel模板上
            Workbook workbook = new XLSTransformer().transformXLS(inputStream, model);
            Sheet sheet = workbook.getSheetAt(0);
            for( CellRangeAddress cellRangeAddress : cellRangeAddressList ){
                sheet.addMergedRegion(cellRangeAddress);
            }
            if (null!=valueList&&valueList.size()>0) {
				for (String str : valueList) {
					String[] split = str.split("-");
					sheet.createRow(Integer.parseInt(split[0])).createCell(Integer.parseInt(split[1])).setCellValue(split[2]);
				}
			}
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            //设置response头信息
            response.setContentType(String.format("%s;charset=utf-8", "application/x"));
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    new String((fileName + ".xls").getBytes("utf-8"), "iso8859-1"));
            response.setHeader("Content-Length", String.valueOf(out.toByteArray().length));
            response.getOutputStream().write(out.toByteArray());
        } catch (IOException | InvalidFormatException e) {
    	    e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 上下合并到一个单元格后只能保留最左上角的数据
     */
    public static void downMergedRegion(int rowIndex, int columnIndex, int toRowIndex, int toColumnIndex,Sheet sheet) {
        if (rowIndex < toRowIndex) {
            for (int i = rowIndex+1; i <= toRowIndex; i++) {
                for (int j = columnIndex; j <= toColumnIndex; j++) {
                    HSSFRow row = (HSSFRow)sheet.getRow(i);
                    if (null != row) {
                        HSSFCell cell = row.getCell(j);
                        if (null != cell) {
                            cell.setCellValue("");
                        }
                    }
                }
            }
        }
    }
    
}
