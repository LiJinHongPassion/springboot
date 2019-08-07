package com.example.li.springboot_poi_demo.utils;

import cn.afterturn.easypoi.cache.manager.POICacheManager;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author ljh
 * @date 2019/8/2
 */
@Component
public class ExcelListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        POICacheManager.setFileLoader(new FileLoaderImpl());
    }
}