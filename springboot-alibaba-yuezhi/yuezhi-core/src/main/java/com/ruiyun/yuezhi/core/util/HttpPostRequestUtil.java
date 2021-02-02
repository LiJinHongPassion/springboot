package com.ruiyun.yuezhi.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONObject;

public class HttpPostRequestUtil {

	public static void main(String[] args) throws Exception {
		URL url = new URL("http://172.16.3.63/poly-appservice/version1/login");
		JSONObject result = httpPostRequest(url, "params={'operatorAccount':'kfs_01','operatorPwd':'1'}");
		System.out.println(result.getString("result"));
	}

	/**
	 * http post请求
	 * 
	 * @param url         地址
	 * @param postContent post内容格式为param1=value&param2=value2&param3=value3
	 * @return
	 * @throws IOException
	 */
	public static JSONObject httpPostRequest(URL url, String postContent) throws Exception {
		OutputStream outputstream = null;
		BufferedReader in = null;
		try {
			URLConnection httpurlconnection = url.openConnection();
			httpurlconnection.setConnectTimeout(10 * 1000);
			httpurlconnection.setDoOutput(true);
			httpurlconnection.setUseCaches(false);
			/*
			 * httpurlconnection .setRequestProperty( "headers",
			 * "4a829357aa2c65f49602cbd31cd742609426943dcd17897adcb2dee45b14de443a6d1b3edab69e47395ca8198d00107eb7ce66744fd6c477fe08c7027ea215ce2baee40caa5969b8be3e5bec341941ca24b5fe0db35706e757af8b75072df32bf5dea6a33cfb0cc58aa2bb4f8f5e1c4f75d99810363d230c728630b4d1d11d49"
			 * );
			 */
			OutputStreamWriter out = new OutputStreamWriter(httpurlconnection.getOutputStream(), "UTF-8");
			out.write(postContent);
			out.flush();

			StringBuffer result = new StringBuffer();
			in = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			return JSONObject.fromObject(result.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("post请求异常：" + ex.getMessage());
		} finally {
			if (outputstream != null) {
				try {
					outputstream.close();
				} catch (IOException e) {
					outputstream = null;
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					in = null;
				}
			}
		}
	}
}
