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
        main3();
    }

    /**
     * 描述: 获取图片链接
     */
    public static void main1() {
        String url = "https://weibo.com/u/5247587289?sudaref=www.baidu.com&display=0&retcode=6102";
        Map<String,String> headers = new HashMap<>();
        headers.put("Remote Address","180.149.134.141:443");
        //解决乱码
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
//        headers.put("accept-encoding", "gzip, deflate, br");
        headers.put("Cookie","SINAGLOBAL=3398784543562.345.1556971387883; ALF=1604025333; SCF=AiP6O1VKY9aVLCoBWlwebldq5oAlcPz_uybNwA9NOkGgbCkrKL_s_6KD7T_SjTL2zbFzwYp_yurYUDswC8JmUKs.; SUHB=06ZVi3QnsQbcSp; SUB=_2AkMqmaEyf8NxqwJRmP4UzGrjbIRwwwHEieKcxVDpJRMxHRl-yT83ql0stRB6ARmP3ABGeenWWMtKOPRGkdfC0E87Ww1L; SUBP=0033WrSXqPxfM72-Ws9jqgMF55529P9D9WWPRq577LDP.w-p-Jbjv-oZ; YF-Page-G0=e57fcdc279d2f9295059776dec6d0214|1573203461|1573203457; _s_tentry=passport.weibo.com; UOR=www.cnblogs.com,widget.weibo.com,www.baidu.com; Apache=2360325663850.9907.1573203463839; ULV=1573203464031:28:6:5:2360325663850.9907.1573203463839:1573021014006");
        headers.put("Host", "weibo.com");
        headers.put("Referer", "https://weibo.com/u/5247587289?sudaref=www.baidu.com&display=0&retcode=6102");

        Set<String> allImgUrl = getAllImgUrl(url,
                "((http|https|HTTP|HTTPS):/)*/[a-zA-Z0-9\\_/\\.]+\\.(jpg|png|gif|jpeg)",
                headers);

        List<String> fileNames = new ArrayList<>();
        for (int i = 0; i < allImgUrl.size() ; i++) {
            fileNames.add(String.valueOf(i));
        }
        defaultGetIPs();
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
