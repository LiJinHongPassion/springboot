package img;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static img.CrawlerImageUtil.*;


/**
 * 描述：采用http2协议爬取图片
 * 网址: https://foter.com/
 *
 * @author LJH
 * @date 2019/11/28-18:10
 * @QQ 1755497577
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {

//        String cookies = "__cfduid=d924c47c295d97149583a1435dffad06f1574994445; __utma=43775666.410682732.1574994447.1574994447.1574994447.1; __utmc=43775666; __utmz=43775666.1574994447.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmt=1; __utmb=43775666.2.10.1574994447; PHPSESSID=11vddiur1cmrpog0mpquc7ikg3";

        int page = 2;
        String url="https://foter.com/abstract";//不能为https://foter.com/abstract/多个斜杠

        while (
                getOnePageImg(getRandomCookie(url, 5),
                "./img/",
                url,
                String.valueOf(page++))
        ){
            System.out.println(url + page + "页面图片获取完成");
        }

    }


    /**
     * 描述: 爬取"url/page"下的所有图片路径
     * @param cookies   用户cookies
     * @param savePath  图片保存路径
     * @param url       模块的url:例如 https://foter.com/buildings/
     * @param page      第几页的图片
     * @return Boolean  是否还有图片,无图片返回false
     */
    private static Boolean getOnePageImg(String cookies, String savePath, String url, String page){
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("accept-language", "zh-CN,zh;q=0.9");
        headers.put("cookie", cookies);
        headers.put("referer", url);
        headers.put("sec-fetch-mode", "cors");
        headers.put("sec-fetch-site", "same-origin");
        headers.put("x-requested-with", "XMLHttpRequest");
        headers.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.75 Safari/537.36");

        Set<String> allImgUrl = getAllImgUrl(url + "/" + page,
                "((http|https|HTTP|HTTPS):/)*/[a-zA-Z0-9\\_/\\.\\-]+\\.(jpg|png|gif|jpeg)",
                headers,
                Optional.of("https://foter.com"));
        if (allImgUrl.size()>0){
//            int i = 0;
//            for(String s : allImgUrl){
//                downloadImg2local(s,
//                        headers,
//                        savePath,
//                        page + "-" + String.valueOf(i++));
//            }
            List<String> fileNames = new ArrayList<>();
            for( int i = 0; i < allImgUrl.size(); i++){
                fileNames.add(page + "-" + i);
            }
            downloadImg2localByMuitThread(allImgUrl, headers, "./img/", fileNames);
            return true;
        }
        return false;
    }

    /**
     * 描述: 文件去重(方法未写)
     */
    public static void main3(){

    }

}
