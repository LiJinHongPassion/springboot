package com.ruiyun.yuezhi.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 配置文件读取工具
 * 
 * @author jiangbo
 *
 */
public class ProFileReader {
	private Properties p;

	public ProFileReader(String fileName) {
		InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
		this.p = new Properties();
		try {
			this.p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ProFileReader(InputStream is) {
		this.p = new Properties();
		try {
			this.p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getParamValue(String param) {
		try {
			return find(this.p.getProperty(param));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 查询${}配置的变量
	 * 
	 * @param value
	 * @return
	 */
	private String find(String value) {
		String expText = "(?<=\\$\\{)[^\\}]+";
		String expSelf = "\\$\\{\\w*\\}";
		Pattern patText = Pattern.compile(expText);
		Matcher matText = patText.matcher(value);

		boolean isFind = false;
		List<String> list = new ArrayList<String>();
		while (matText.find()) {
			if (StringUtils.isEmpty(getParamValue(matText.group()))) {
				continue;
			}
			list.add(getParamValue(matText.group()));
			isFind = true;
		}

		String[] tmp = null;
		if (isFind) {
			tmp = value.split(expSelf);

			if ((tmp.length == list.size()) || (tmp.length - list.size() == 1)) {
				String _value = "";
				for (int i = 0; i < list.size(); i++) {
					_value = _value + tmp[i] + (String) list.get(i);
				}

				if (list.size() < tmp.length) {
					_value = _value + tmp[list.size()];
				}

				return _value;
			}
		}

		return value;
	}
}
