package com.jihu.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转控制器
 * @author jihu
 */
@Controller
public class IndexController {

    @GetMapping("/")
    @ApiOperation(value = "登录页面跳转接口",notes = "跳转到登录页面")
    public String login(){
        return "login";
    }

    @GetMapping("/auth/index")
    @ApiOperation(value = "跳转到权限系统的主页面",notes = "跳转到权限系统的主页面")
    public String index(){
        return "auth/index";
    }

    @ApiOperation(value = "跳转到用户列表接口",notes = "跳转到用户列表")
    @GetMapping("/user/userlist")
    public String userlist(){
        return "user/userlist";
    }

    @ApiOperation(value = "跳转到权限列表接口",notes = "跳转到权限列表")
    @GetMapping("/role/rolelist")
    public String rolelist(){
        return "role/rolelist";
    }

    @ApiOperation(value = "跳转到许可列表接口",notes = "跳转到许可列表")
    @GetMapping("/permission/permissionlist")
    public String permissionlist(){
        return "permission/permissionlist";
    }


}
