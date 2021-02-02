package com.ruiyun.yuezhi.core.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 经纬度相关工具类
 * 
 */
public class LatAndLongUtil {

	private static double EARTH_RADIUS = 6378.137; // 地球半径,km

	/**
	 * 弧度
	 * 
	 * @param d
	 * @return
	 */
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 计算地球上任意两点(经纬度)距离
	 * 
	 * @param lat1
	 *            第一点的纬度
	 * @param lng1
	 *            第一点的经度
	 * @param lat2
	 *            第二点的纬度
	 * @param lng2
	 *            第二点的经度
	 * @return 返回距离，单位：米
	 */
	public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 1000);
		return s;
	}

	/**
	 * 根据经纬度和半径计算经纬度范围
	 * 
	 * @param lat
	 *            纬度
	 * @param lon
	 *            经度
	 * @param raidus
	 *            半径,m
	 * @return 最小纬度：minLat, 最小经度minLng, 最大纬度：maxLat, 最大经度：maxLng
	 */
	public static Map<String, Double> getAround(double lat, double lon, int raidus) {
		try {
			Map<String, Double> rsMap = new HashMap<String, Double>();
			Double latitude = lat;
			Double longitude = lon;

			// 获取每度
			Double degree = (24901 * 1609) / 360.0;
			double raidusMile = raidus;

			Double dpmLat = 1 / degree;
			Double radiusLat = dpmLat * raidusMile;
			Double minLat = latitude - radiusLat;
			Double maxLat = latitude + radiusLat;

			Double mpdLng = degree * Math.cos(rad(latitude));
			Double dpmLng = 1 / mpdLng;
			Double radiusLng = dpmLng * raidusMile;
			Double minLng = longitude - radiusLng;
			Double maxLng = longitude + radiusLng;

			rsMap.put("minLat", minLat);
			rsMap.put("minLng", minLng);
			rsMap.put("maxLat", maxLat);
			rsMap.put("maxLng", maxLng);
			return rsMap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public static void main(String[] args) {
		double distance = LatAndLongUtil.getDistance( Double.valueOf("37.480563"),Double.valueOf("121.467113"),  Double.valueOf("37.480591"),Double.valueOf("121.467926"));
		System.out.println(distance);
		
		Map<String, Double> map = LatAndLongUtil.getAround(Double.valueOf("29.606849"), Double.valueOf("106.556243"), 2000);
		System.out.println(map.get("minLat").toString());
		System.out.println(map.get("minLng").toString());
		System.out.println(map.get("maxLat").toString());
		System.out.println(map.get("maxLng").toString());
	}
}
