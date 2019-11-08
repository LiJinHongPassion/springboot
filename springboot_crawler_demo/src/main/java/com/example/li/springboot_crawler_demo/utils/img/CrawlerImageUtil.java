package com.example.li.springboot_crawler_demo.utils.img;

import com.example.li.springboot_crawler_demo.utils.img.fileMsg.ReadLine;
import com.github.kevinsawicki.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.li.springboot_crawler_demo.utils.img.fileMsg.FileUtil.getFile;
import static com.example.li.springboot_crawler_demo.utils.img.fileMsg.FileUtil.input2byte;
import static com.example.li.springboot_crawler_demo.utils.img.fileMsg.TypeDict.getFileType;

/**
 * 描述：图片爬虫并下载
 *
 * @author LJH
 * @date 2019/11/8-13:47
 * @QQ 1755497577
 */
public class CrawlerImageUtil {

    private static Logger logger = LoggerFactory.getLogger(CrawlerImageUtil.class);
    static ReadLine rl = new ReadLine();
    static {
        rl.initList("C:\\Users\\Li\\Desktop\\springboot_crawler_demo\\src\\main\\java\\com\\example\\li\\springboot_crawler_demo\\utils\\img\\fileMsg\\user_agents.txt");
    }



    /**
     * 描述: 爬虫 -- 获取页面中所有图片链接
     *
     * @author LJH-1755497577 2019/11/8 15:57
     * @param url   非图片链接，例如：http://www.baidu.com/artical=424
     * @param regx  正则表法式子
     * @return java.util.List<java.lang.String>
     */
    public static Set<String> getAllImgUrl(String url, String regx, Map<String, String> headers){
        String body = HttpRequest.get(url).headers(headers).body();
        body = body.replaceAll(" ", "")
                .replaceAll("\r\n", "")
                .replaceAll("\t","")
                .replaceAll("\\\\","");

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(regx);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(body);
        //创建list存储
        Set<String> re = new HashSet<>();

        while(m.find()) {
            try {
                re.add(m.group().replaceAll("((http|https|HTTP|HTTPS):)*//", "https://"));
            }catch (Exception e){
            }
        }

        return re;
    }


    /**
     * 描述: 下载 -- 不用设置headers中的accept，user-agent
     *
     * @author LJH-1755497577 2019/11/8 15:00
     * @param imgUrl    图片url
     * @param savePath  本地保存路径
     * @param fileName  文件名
     * @return java.lang.String
     */
    public static String downloadImg2local(String imgUrl, String savePath, String fileName) {
        return downloadImg2local(imgUrl, new HashMap<>(), savePath, fileName);
    }

    /**
     * 描述: 下载 -- 默认header，随机设置user-agent
     *
     * @author LJH-1755497577 2019/11/8 15:00
     * @param imgUrl    图片url
     * @param savePath  本地保存路径
     * @param fileName  文件名
     * @param headers   请求头
     * @return java.lang.String
     */
    public static String downloadImg2local(String imgUrl, Map<String, String> headers, String savePath, String fileName) {
        headers.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        //解决乱码
        //headers.put("accept-encoding", "gzip, deflate, br");
//        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.75 Safari/537.36");
        headers.put("user-agent", rl.getStringOfFile());

        return defaultDownloadImg2local(imgUrl, headers, savePath, fileName);
    }

    /**
     * 描述: 下载 -- 根据图片路径保存至本地
     *
     * @author LJH-1755497577 2019/11/8 14:03
     * @param imgUrl    图片url
     * @param savePath  本地保存路径
     * @param fileName  文件名
     * @param headers   请求头
     * @return java.lang.String 本地图片路径
     */
    public static String defaultDownloadImg2local(String imgUrl, Map<String, String> headers, String savePath, String fileName){
        String fileType = null;
        try {
            //1.获取图片流
            InputStream stream = HttpRequest.get(imgUrl).headers(headers).stream();
            byte[] fileByte = input2byte(stream);
            //2.根据文件流获取文件类型
            fileType = getFileType(fileByte.clone());
            if(fileType == null || fileType.equals("0000")){
                throw new Exception();
            }
            //3.保存文件
            getFile(fileByte, savePath, fileName + "." + fileType);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("\n失败 ======== imgUrl:" + imgUrl + "\n\t原因：文件类型为空 或 headers有问题 或 IO异常");
            return null;
        }
        return savePath + "/" + fileName + "." + fileType;
    }

}
