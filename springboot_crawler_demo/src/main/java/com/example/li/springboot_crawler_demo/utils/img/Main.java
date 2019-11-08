package com.example.li.springboot_crawler_demo.utils.img;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.example.li.springboot_crawler_demo.utils.img.CrawlerImageUtil.downloadImg2local;
import static com.example.li.springboot_crawler_demo.utils.img.CrawlerImageUtil.getAllImgUrl;

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
        String url = "https://weibo.com/u/5247587289?sudaref=www.baidu.com&display=0&retcode=6102";
        Map<String,String> headers = new HashMap<>();
        headers.put("Remote Address","180.149.134.141:443");
        headers.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        //解决乱码
        headers.put("Accept-Language", "zh-CN,zh;q=0.9");
//        headers.put("accept-encoding", "gzip, deflate, br");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.75 Safari/537.36");
        headers.put("Cookie","SINAGLOBAL=3398784543562.345.1556971387883; ALF=1604025333; SCF=AiP6O1VKY9aVLCoBWlwebldq5oAlcPz_uybNwA9NOkGgbCkrKL_s_6KD7T_SjTL2zbFzwYp_yurYUDswC8JmUKs.; SUHB=06ZVi3QnsQbcSp; SUB=_2AkMqmaEyf8NxqwJRmP4UzGrjbIRwwwHEieKcxVDpJRMxHRl-yT83ql0stRB6ARmP3ABGeenWWMtKOPRGkdfC0E87Ww1L; SUBP=0033WrSXqPxfM72-Ws9jqgMF55529P9D9WWPRq577LDP.w-p-Jbjv-oZ; YF-Page-G0=e57fcdc279d2f9295059776dec6d0214|1573203461|1573203457; _s_tentry=passport.weibo.com; UOR=www.cnblogs.com,widget.weibo.com,www.baidu.com; Apache=2360325663850.9907.1573203463839; ULV=1573203464031:28:6:5:2360325663850.9907.1573203463839:1573021014006");
        headers.put("Host", "weibo.com");
        headers.put("Referer", "https://weibo.com/u/5247587289?sudaref=www.baidu.com&display=0&retcode=6102");

        Set<String> allImgUrl = getAllImgUrl(url,
                "((http|https|HTTP|HTTPS):/)*/[a-zA-Z0-9\\_/\\.]+\\.(jpg|png|gif|jpeg)",
                headers);

        int i = 0;
        for(String s : allImgUrl){
            downloadImg2local(s,
                    "./img/", String.valueOf(i++));
        }

    }

    /**
     * 描述: 下载图片
     */
    public static void main2(){
        downloadImg2local("https://wx3.sinaimg.cn/thumb150/005J8laFly1fuxdbmsfysj30u0190kg1.jpg",
                "./img/", "ttt");

    }
    /**
     * 描述: 文件去重(方法未写)
     */
    public static void main3(){

    }
}
