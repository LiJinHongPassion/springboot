package com.ruiyun.yuezhi.core.util;

/**
 * Copyright © 2016 重庆锐云科技有限公司  All rights reserved
 * 
 * 分页方言
 * @author HKCHEN
 * @version 1.0 
 */
public abstract class Dialect {

    public abstract String getLimitString(String sql, int skipResults, int maxResults);
    
}
