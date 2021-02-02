package com.ruiyun.yuezhi.core.util;

/**
 * Copyright © 2016 重庆锐云科技有限公司 All rights reserved
 * 
 * 分页oracle实现
 * 
 * @author HKCHEN
 * @version 1.0
 */
public class OracleDialect extends Dialect {

	@Override
	public String getLimitString(String sql, int skipResults, int maxResults) {
		sql = sql.trim();
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		pagingSelect.append(sql);
		pagingSelect.append(" ) row_ ) where rownum_ > ").append(skipResults).append(" and rownum_ <= ").append(skipResults + maxResults);
		// System.out.println("分页SQL=="+pagingSelect.toString());
		return pagingSelect.toString();
	}

}
