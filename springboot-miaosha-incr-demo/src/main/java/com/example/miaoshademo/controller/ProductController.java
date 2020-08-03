package com.example.miaoshademo.controller;

import com.example.miaoshademo.service.ProductService;
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
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * 获取商品信息
     * @return
     */
    @RequestMapping("get")
    public String get(){
        return productService.get();
    }


    /**
     *  常规秒杀商品
     * @param key 购买的商品ID
     * @return
     */
    @RequestMapping("buy")
    public String buy(@RequestParam("num") Integer num, @RequestParam("key") Integer key){
        return productService.buy(num, key);
    }

    /**
     * 秒杀前准备, 将秒杀商品的数据放至redis
     * @return
     */
    @RequestMapping("ready")
    public String ready(){
        return productService.ready();
    }

    /**
     *  加入了redis来秒杀商品
     * @param key 购买的商品ID
     * @return
     */
    @RequestMapping("buy1")
    public String buy1(@RequestParam("num") Integer num, @RequestParam("key") Integer key){
        return productService.buy1(num, key);
    }

}
