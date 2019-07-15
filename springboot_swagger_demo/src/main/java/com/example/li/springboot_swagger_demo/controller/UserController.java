package com.example.li.springboot_swagger_demo.controller;

import com.example.li.springboot_swagger_demo.entity.dto.UserDTO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author LJH
 * @date 2019/7/15-10:36
 */
@RestController
@Api(value = "用户模块")//板块描述
public class UserController {

    @GetMapping("/hello")
    @ApiOperation(value = "测试Swagger", notes = "hello方法")
    public String hello(){
        return "hello SpringBoot-swagger";
    }


    /**
     * 方法1：采用@ApiImplicitParams描述参数，当以实体类接收传递的参数时，需要添加@ApiIgnore()
     * @param id
     * @param user 传递的实体类
     * @return
     */

    @GetMapping("/test")
    @ApiOperation(value = "测试", notes = "测试swagger注解")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "用户名字", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "sex", value = "用户性别", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "age", value = "用户年龄", required = true, dataType = "int", paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied",
                    responseHeaders = @ResponseHeader(name = "X-Rack-Cache", description = "Explains whether or not a cache was used", response = Boolean.class)),
            @ApiResponse(code = 404, message = "Pet not found") })
    public UserDTO test(@RequestParam(value = "id", required = false) String id,
                        @ApiIgnore() UserDTO user){

        user.setName("OK");
        return user;
    }

    /**
     * 方法1：采用@ApiParam描述参数，实体类的属性描述可以利用model的@ApiModelProperty、@ApiModel注解
     * @param id
     * @param user 传递的实体类
     * @return
     */

    @GetMapping("/test1")
    @ApiOperation(value = "测试1", notes = "测试swagger注解1")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied",
                    responseHeaders = @ResponseHeader(name = "X-Rack-Cache", description = "Explains whether or not a cache was used", response = Boolean.class)),
            @ApiResponse(code = 404, message = "Pet not found") })
    public UserDTO test1(@RequestParam(value = "id", required = false) @ApiParam(value = "用户的id", name = "用户的id", required = false) String id,
                        @ApiParam(value = "用户的Dto", name = "用户的对象", required = true) UserDTO user){

        user.setName("OK");
        return user;
    }
}