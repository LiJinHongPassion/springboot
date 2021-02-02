package com.ruiyun.yuezhi.core.util;

import java.net.URLDecoder;

public class ClassPathUtil {
	public static String getPath() {
		String path = null;
		try {
			path = ClassPathUtil.class.getClassLoader().getResource("").getPath().toString();
			String systemName = System.getProperty("os.name");
			if (systemName.startsWith("Windows")) {
				path = URLDecoder.decode(path.substring(1), "utf-8");
			} else {
				path = URLDecoder.decode(path, "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}
}
