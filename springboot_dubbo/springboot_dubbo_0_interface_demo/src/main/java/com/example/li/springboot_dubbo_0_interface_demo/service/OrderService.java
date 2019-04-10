package com.example.li.springboot_dubbo_0_interface_demo.service;


import com.example.li.springboot_dubbo_0_interface_demo.bean.UserAddress;

import java.util.List;

public interface OrderService {

	/**
	 * 初始化订单
	 * @param userId
	 */
	public List<UserAddress> initOrder(String userId);

}
