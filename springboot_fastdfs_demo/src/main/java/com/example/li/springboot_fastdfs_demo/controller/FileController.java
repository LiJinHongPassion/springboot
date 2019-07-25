package com.example.li.springboot_fastdfs_demo.controller;

import com.example.li.springboot_fastdfs_demo.utils.FastDFSClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


/**
 * @author LJH
 * @date 2019/7/25-16:23
 * @QQ 1755497577
 */
@RequestMapping("/file")
@RestController
public class FileController {

    /**
     * 描述: 上传文件
     *
     * @author LJH-1755497577 2019/7/25 19:16
     * @param multipartFile
     * @return java.lang.String 成功返回组名和ID
     */
    @PostMapping("/upload")
    public String uploadFile( @RequestParam("file") MultipartFile multipartFile) {
        File savefile = new File("D:\\" + multipartFile.getOriginalFilename());
        FastDFSClient fastDFSClient = null;

        try {
            multipartFile.transferTo(savefile);
            fastDFSClient = new FastDFSClient();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] strings = fastDFSClient.uploadFile(savefile,
                multipartFile.getOriginalFilename()+System.currentTimeMillis());
        return strings[0] + "/" + strings[1];
    }


    /**
     * 描述: 删除文件
     *
     * @author LJH-1755497577 2019/7/25 19:47
     * @param groupName group名字 例子：group1
     * @param fileId 文件id 例子：M00/00/00/wKgaZV05lKCAcqUZAAEvZ9-NEB87.1.png
     * @return java.lang.String
     */
    @PostMapping("/del")
    public String delectFile( @RequestParam("groupName") String groupName,
                              @RequestParam("fileId") String fileId) {

        FastDFSClient fastDFSClient = null;

        try {

            fastDFSClient = new FastDFSClient();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int reulst = fastDFSClient.deleteFile(groupName, fileId);
        return String.valueOf(reulst);
    }

}
