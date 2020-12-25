package com.codeant.ocean.controller;

import com.codeant.ocean.mapper.account.UserMapper;
import com.codeant.ocean.mapper.iceberg.ItemOrderMapper;
import com.codeant.ocean.mapper.ocean.TotalMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ca")
public class TestView {

    /**
     * 不推荐直接在controller层引入Dao层。这里只做演示使用
     */
    @Resource
    private UserMapper userMapper;
    @Resource
    private ItemOrderMapper itemOrderMapper;
    @Resource
    private TotalMapper totalMapper;

    @GetMapping("/test")
    public String test() {
        System.out.println("用户数：" + userMapper.selectCountUser(null, null));
        System.out.println("订单数：" + itemOrderMapper.selectCountSuccessOrder(null, null));
        System.out.println("数：" + totalMapper.selectAllCount());
        return "ok";
    }
}
