package com.example.miaoshademo.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * codeant_product_log
 * @author
 */
public class CodeantProductLog implements Serializable {
    private Integer id;

    /**
     * 用时间戳来展示, 可以体现在相同时间下单的效果
     */
    private Date createTime;

    private Integer productId;

    /**
     * 购买数量
     */
    private Integer num;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public CodeantProductLog(Date createTime, Integer productId, Integer num) {
        this.createTime = createTime;
        this.productId = productId;
        this.num = num;
    }
}