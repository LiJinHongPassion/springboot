package com.ruiyun.yuezhi.core.util;

/**
 * Copyright © 2016 重庆锐云科技有限公司 All rights reserved
 * 
 * 分页MySql实现
 * 
 * @author HKCHEN
 * @version 1.0
 */
public class MySqlDialect extends Dialect {

	@Override
	public String getLimitString(String sql, int skipResults, int maxResults) {
		sql = sql.trim();
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		pagingSelect.append(sql);
		pagingSelect.append(" limit ").append(skipResults).append(" , ").append(maxResults);

		return pagingSelect.toString();
	}
}
