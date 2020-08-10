package com.example.miaoshademo.busi.controller;

import com.example.miaoshademo.busi.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.7.31
 */
@RestController
public class BillController {
    @Autowired
    private BillService billService;


    /**
     *  常规秒杀商品
     * @param id 账单
     * @return
     */
    @RequestMapping("pay")
    public String pay(@RequestParam("id") Integer id){
        return billService.pay(id);
    }

}
