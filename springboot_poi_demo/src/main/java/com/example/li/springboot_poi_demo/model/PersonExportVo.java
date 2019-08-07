package com.example.li.springboot_poi_demo.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author ljh
 * @date 2019/8/2
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class PersonExportVo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 姓名
     */
    @Excel(name = "姓名", orderNum = "0", width = 15)
    private String name;

    /**
     * 登录用户名
     */
    @Excel(name = "用户名", orderNum = "1", width = 15)
    private String username;

    @Excel(name = "手机号码", orderNum = "2", width = 15)
    private String phoneNumber;

    /**
     * 人脸图片
     */
    @Excel(name = "人脸图片", orderNum = "3", width = 15, height = 30, type = 2, savePath = "")
    private String imageUrl;
}