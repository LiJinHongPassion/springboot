package com.example.li.springboot_crawler_demo.utils.img;

import com.example.li.springboot_crawler_demo.utils.img.md5.MD5Util2;

import java.io.File;
import java.util.*;

import static com.example.li.springboot_crawler_demo.utils.img.CrawlerImageUtil.*;

/**
 * 描述：
 *
 * @author LJH
 * @date 2019/11/8-18:10
 * @QQ 1755497577
 */
public class Main {
    public static void main(String[] args) {
        main1();
    }

    /**
     * 描述: 获取图片链接
     */
    public static void main1() {
        String url = "https://weibo.com/u/6580966381";
        Map<String,String> headers = new HashMap<>();
        headers.put("Remote Address","180.149.134.141:443");
        //解决乱码
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
//        headers.put("accept-encoding", "gzip, deflate, br");
        headers.put("Cookie","SINAGLOBAL=9917422063042.697.1574829402700; _s_tentry=www.mobiletrain.org; SUB=_2AkMquX1Df8NxqwJRmPAQxGLkaopxzwzEieKc5YyYJRMxHRl-yT83ql4rtRB6ATlTrG8oEMvFOKbi7y1QJGYbVHTKjif8; SUBP=0033WrSXqPxfM72-Ws9jqgMF55529P9D9W5i4TjASxNGAo9DZ69y7OQp; UOR=tophub.today,s.weibo.com,www.baidu.com; Apache=4274527537330.8877.1575350901043; ULV=1575350901074:3:1:1:4274527537330.8877.1575350901043:1574904407114; YF-Page-G0=afcf131cd4181c1cbdb744cd27663d8d|1575351666|1575351666; login_sid_t=6172ebd564ec94b9a77dd44a432aff74; cross_origin_proto=SSL; Ugrow-G0=589da022062e21d675f389ce54f2eae7; YF-V5-G0=27518b2dd3c605fe277ffc0b4f0575b3; WBStorage=42212210b087ca50|undefined; wb_view_log=1920*10801");
        headers.put("Host", "weibo.com");
        headers.put("Referer", "https://s.weibo.com/weibo/%25E6%25A0%25A1%25E8%258A%25B1?topnav=1&wvr=6&topsug=1");

        Set<String> allImgUrl = getAllImgUrl(url,
                "((http|https|HTTP|HTTPS):/)*/[a-zA-Z0-9\\_/\\.]+\\.(jpg|png|gif|jpeg)",
                headers);

        List<String> fileNames = new ArrayList<>();
        for (int i = 0; i < allImgUrl.size() ; i++) {
            fileNames.add(String.valueOf(i));
        }
        downloadImg2localByMuitThread(allImgUrl, headers, "./img/", fileNames, 100);


    }

    /**
     * 描述: 文件去重(方法未写)
     */
    public static void main3(){
        //0与00为同一图片,只是名字不一样
        //0与1为不同图片,名字不同
        String md5Hashcode0 = MD5Util2.getFileMD5(new File("./img/0.jpg"));
        String md5Hashcode00 = MD5Util2.getFileMD5(new File("./img/00.jpg"));
        String md5Hashcode1 = MD5Util2.getFileMD5(new File("./img/1.jpg"));
        System.out.println("00 & 0  " + md5Hashcode00.equals(md5Hashcode0));
        System.out.println("1 & 0   " + md5Hashcode1.equals(md5Hashcode0));
    }
}
