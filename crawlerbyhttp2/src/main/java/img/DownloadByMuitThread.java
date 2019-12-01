package img;

import java.util.Map;

import static img.CrawlerImageUtil.downloadImg2local;

/**
 * 描述: 下载线程
 *
 * @author lijinhong
 * @date 19.11.29
 */
public class DownloadByMuitThread implements Runnable {
    private String url;
    private String fileName;
    private String savePath;
    private Map<String, String> headers;

    private DownloadByMuitThread(String url, String fileName, String savePath, Map<String, String> headers) {
        this.url = url;
        this.fileName = fileName;
        this.savePath = savePath;
        this.headers = headers;
    }

    public static DownloadByMuitThread getInstance(String url, String fileName, String savePath, Map<String, String> headers){
        return new DownloadByMuitThread( url,  fileName,  savePath,  headers);
    }

    @Override
    public void run() {
        downloadImg2local(url, headers, savePath, fileName);
    }
}
