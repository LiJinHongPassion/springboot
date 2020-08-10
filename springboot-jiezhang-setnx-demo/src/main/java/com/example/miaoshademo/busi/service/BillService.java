package com.example.miaoshademo.busi.service;

import com.example.miaoshademo.lock.anno.SyncAnnotation;
import com.example.miaoshademo.lock.anno.SyncEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.7.31
 */
@Service
public class BillService {


    @SyncAnnotation(SyncEnum.CHECKOUT_SYNC)
    @Transactional
    public String pay(Integer billId){
        //模拟处理账单逻辑花费5s
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "结账完成!";
//        return billDao.updateByPrimaryKey(bill) > 0 ? "成功" : "失败";
    }
}
