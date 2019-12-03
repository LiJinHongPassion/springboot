package com.example.li.springboot_crawler_demo.utils.img.fileMsg;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author ljh TODO 随机读取user_agents.txt文件内容
 *
 */
public class ReadLine {
 
    List<String> list = new ArrayList<String>();
 
    /**
     * 获取随机行数
     * 
     * @param total
     *            文件总行数
     * @return 整形参数
     */
    public int getRandomNumber(int total) {
        return (int) (Math.random() * total);
    }
 
    /**
     * 将文件内容按行读取存放到List里面
     * 
     * @param fileName
     *            文件名
     */
    public void initList(String fileName) {
        try {
            RandomAccessFile accessFile = new RandomAccessFile(fileName, "r");
 
            String str = "";
 
            while (null != (str = accessFile.readLine())) {
                list.add(str);
            }
 
            accessFile.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
 
    /**
     * 获取随机行数的字符串
     * 
     * @return
     */
    public String getStringOfFile() {
 
        if (null != list) {
            int line = getRandomNumber(list.size());
 
            return list.get(line);
        }
        return null;
 
    }


    public List<String> getList() {
        return list;
    }

}