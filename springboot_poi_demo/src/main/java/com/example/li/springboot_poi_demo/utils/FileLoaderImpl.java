package com.example.li.springboot_poi_demo.utils;

import cn.afterturn.easypoi.cache.manager.IFileLoader;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 文件加载类,根据路径加载指定文件
 *
 * @author user
 * @date 2018/12/18
 */
public class FileLoaderImpl implements IFileLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(cn.afterturn.easypoi.cache.manager.FileLoaderImpl.class);

    @Override
    public byte[] getFile(String url) {
        InputStream fileis = null;
        ByteArrayOutputStream baos = null;
        try {

            //判断是否是网络地址
            if (url.startsWith("http") || url.startsWith("https")) {
                URL urlObj = new URL(url);
                URLConnection urlConnection = urlObj.openConnection();
                //防止报403的错误
                urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
                //设置链接超时时间
                urlConnection.setConnectTimeout(3000);
                //设置读取时间
                urlConnection.setReadTimeout(6000);
                urlConnection.setDoInput(true);
                fileis = urlConnection.getInputStream();
            } else {
                //先用绝对路径查询,再查询相对路径
                try {
                    fileis = new FileInputStream(url);
                } catch (FileNotFoundException e) {
                    //获取项目文件
                    fileis = FileLoaderImpl.class.getClassLoader().getResourceAsStream(url);
                    if (fileis == null) {
                        fileis = FileLoaderImpl.class.getResourceAsStream(url);
                    }
                }
            }
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fileis.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos.toByteArray();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(fileis);
            IOUtils.closeQuietly(baos);
        }
        LOGGER.error(fileis + "这个路径文件没有找到,请查询");
        return null;
    }
}