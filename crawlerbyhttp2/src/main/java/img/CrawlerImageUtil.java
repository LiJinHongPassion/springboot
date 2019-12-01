package img;

import img.fileMsg.ReadLine;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.KeyException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static img.fileMsg.FileUtil.getFile;
import static img.fileMsg.FileUtil.input2byte;
import static img.fileMsg.TypeDict.getFileType;


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
        rl.initList("C:\\Users\\Administrator\\Desktop\\crawlerbyhttp2\\src\\main\\java\\img\\fileMsg\\user_agents.txt");
    }



    /**
     * 描述: 爬虫 -- 获取页面中所有图片链接
     *
     * @author LJH-1755497577 2019/11/8 15:57
     * @param url   非图片链接，例如：http://www.baidu.com/artical=424
     * @param regx  正则表法式子
     * @return java.util.List<java.lang.String>
     */
    public static Set<String> getAllImgUrl(String url, String regx, Map<String, String> headers, Optional<String> webSite){
            String body = getResponseBodyByGET(url, headers);
            body = body.replaceAll(" ", "")
                    .replaceAll("\r\n", "")
                    .replaceAll("\t","")
                    .replaceAll("\\\\","");

            // 创建 Pattern 对象
            Pattern r = Pattern.compile(regx);
            // 现在创建 matcher 对象
            Matcher m = r.matcher(body);
            //创建list存储
            Set<String> re = new HashSet<String>();

            while(m.find()) {
                try {
                    String iurl = m.group();
                    if(!iurl.toLowerCase().contains("http"))
                        re.add(webSite
                                .orElse("http://")
                                .concat(iurl.replaceAll("((http|https|HTTP|HTTPS):)*//", "")
                                )
                        );
                    else
                        re.add(iurl);
                }catch (Exception e){
                }
            }
            return re;
    }


    /**
     * 描述: 多线程下载 -- 图片多线程下载
     * @param imgUrls   图片链接
     * @param headers   请求头
     * @param savePath  保存路径
     * @param fileNames 文件名
     */
    public static void downloadImg2localByMuitThread(Set<String> imgUrls, Map<String, String> headers, String savePath, List<String> fileNames){
        List<String> urls = imgUrls.stream().collect(Collectors.toList());

        //创建一个可缓存线程池
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < imgUrls.size(); i++) {
            try {
                //sleep可明显看到使用的是线程池里面以前的线程，没有创建新的线程
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println("Thread - " + i + "  =============== >  " + urls.get(i));
            cachedThreadPool.execute(DownloadByMuitThread.getInstance(urls.get(i), fileNames.get(i), savePath, headers));
        }
        //关闭线程池
        cachedThreadPool.shutdown();
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
        return downloadImg2local(imgUrl, new HashMap<String, String>(), savePath, fileName);
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
            byte[] fileByte = defaultDownloadFile( imgUrl, headers);

            //2.根据文件流获取文件类型
            fileType = getFileType(fileByte.clone());
            if(fileType == null || fileType.equals("0000")){
                throw new Exception();
            }
            //3.保存文件
            getFile(fileByte, savePath, fileName + "." + fileType);
            System.out.println(savePath + "/" + fileName + "." + fileType + " ===========> 下载成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\n失败 ======== imgUrl:" + imgUrl + "\n\t原因：文件类型为空 或 文件url错误 或 headers有问题 或 IO异常");
            return null;
        }
        return savePath + "/" + fileName + "." + fileType;
    }

    /**
     * 描述: 下载 -- http2协议返回文件流
     * @param url
     * @param headers
     * @return
     */
    public static byte[] defaultDownloadFile(String url, Map<String, String> headers){
        try {
            return HttpClient
                    .newHttpClient()
                    .send(
                            HttpRequest
                                    .newBuilder(new URI(url))
                                    .headers(map2String(headers))
                                    .GET()
                                    .build(),
                            HttpResponse
                                    .BodyHandler.asByteArray()
                    ).body();
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 描述:Get请求返回response的body
     * @param url
     * @param headers
     * @return
     */
    private static String getResponseBodyByGET(String url,  Map<String, String> headers) {
        return getResponseByGET(url, headers).body();
    }

    /**
     * 描述:Get请求返回response
     * @param url
     * @param headers
     * @return
     */
    private static HttpResponse<String> getResponseByGET(String url,  Map<String, String> headers){
        try {
            return HttpClient
                    .newHttpClient()
                    .send(
                            HttpRequest
                                    .newBuilder(new URI(url))
                                    .headers(map2String(headers))
                                    .GET()
                                    .build(),
                            HttpResponse
                                    .BodyHandler
                                    .asString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 描述: 工具 -- 用于转换headers
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    private static <K,V> String[] map2String(Map<K, V> map){
        List<String> stringList = new ArrayList<>();
        map.entrySet()
                .stream()
                .forEach(temp -> {
                    stringList.add(temp.getKey().toString());
                    stringList.add(temp.getValue().toString());
                });
        String[] split = new String[stringList.size()];
        for (int i = 0; i < split.length; i++) {
            split[i] = stringList.get(i);
        }
        return split;
    }
}