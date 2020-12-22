package com.example.codeant.springbootsyncdemo.async;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 描述:
 *
 * @author lijinhong
 * @date 20.12.22
 */
@RestController
@RequestMapping("test")
public class Controller {

    @Resource
    private SyncService syncService;

    @RequestMapping("sync")
    public String test(){
        return syncService.testSync("test");
    }
}
