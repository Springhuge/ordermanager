package com.jihu.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jihu.dto.MPage;
import com.jihu.entity.User;
import com.jihu.service.UserService;
import com.jihu.util.Const;
import com.jihu.util.MyStringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户接口
 * @author jihu
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户控制器")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 测试方法
     */
    @ApiOperation(value = "Swagger2测试接口",notes = "Swagger2测试")
    @PostMapping("/hello")
    public String hello(String name){
        return "hello";
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @ApiOperation(value = "登录接口",notes = "登录请求")
    @ResponseBody
    @PostMapping("/login")
    public Map<String, Object> login(User user, HttpSession session){
        Map<String,Object> message = new HashMap<String,Object>();
        try {
            boolean flag = userService.queryLogin(user);
            if(flag){
                //登录成功
                message.put("message","ok");
                session.setAttribute(Const.LOGIN_USER,user);
            }else {
                message.put("message","用户名或密码错误！！！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.put("error","程序异常！！！");
        }
        return  message;
    }

    @ApiOperation(value = "注销接口",notes = "注销请求")
    @GetMapping("/logout")
    public Object logout(HttpSession session){
        Map<String,Object> message  = new HashMap<String,Object>();
        try {
            session.removeAttribute(Const.LOGIN_USER);
            message.put("message","ok");
        } catch (Exception e) {
            e.printStackTrace();
            message.put("message","注销异常！");
        }
        return message;
    }

    @ApiOperation(value = "用户列表接口",notes = "获取所有的用户")
    @PostMapping("/getAllUser")
    public Object getAllUser(@RequestParam(value = "pageIndex",required = false,defaultValue = "1") Integer pageIndex,
                             @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize
                            ,String queryText){
        Map<String,Object> message = new HashMap<String,Object>();

        try {
            MPage<User>  pages= userService.getAllUsers(pageIndex,pageSize,queryText);
            message.put("page",pages);
            message.put("message","ok");
        } catch (Exception e) {
            e.printStackTrace();
            message.put("error","程序异常");
        }
        return message;
    }

    @ApiOperation(value = "添加用户接口",notes = "添加用户")
    @PostMapping("/addUser")
    public Object addUser(User user){
        Map<String,Object> message = new HashMap<String,Object>();

        try {
            long row = userService.insertUser(user);
            if(row > 0){
               message.put("message","ok");
            }else {
                message.put("message","插入失败！！！");
            }
        }catch (Exception e){
            e.printStackTrace();
            message.put("error","程序异常");
        }
        return message;
    }

    @ApiOperation(value = "物理删除用户接口",notes = "物理删除用户")
    @DeleteMapping("/deleteUser")
    public Object deleteUsers(String ids){
        Map<String,Object> message = new HashMap<String, Object>();
        try {
            long row = userService.removeUsers(ids);
            if(row > 0){
                message.put("message","ok");
            }else{
                message.put("message","删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.put("error","程序异常");
        }
        return message;
    }

    @ApiOperation(value = "逻辑删除用户接口",notes = "逻辑删除用户")
    @PostMapping("/deleteLogicUsers")
    public Object deleteLogicUsers(String ids){
        Map<String,Object> message = new HashMap<String, Object>();
        try {
            long row = userService.removeLogicUsers(ids);
            if(row > 0){
                message.put("message","ok");
            }else{
                message.put("message","删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            message.put("error","程序异常");
        }
        return message;
    }

    @ApiOperation(value = "根据id获取用户接口",notes = "根据id获取用户接口，不可获取到已经逻辑删除的用户")
    @GetMapping("/getUserById")
    public Object getUserById(String id){
        Map<String,Object> message = new HashMap<String,Object>();

        try {
            User user = userService.getUserById(id);
            if(user != null){
                message.put("message","ok");
                message.put("user",user);
            }else{
                message.put("message","用户不存在或者已被逻辑删除！！！");
            }
        }catch (Exception e){
            e.printStackTrace();
            message.put("error","程序异常");
        }
        return message;
    }

    @ApiOperation(value = "修改用户接口",notes = "修改用户信息")
    @PutMapping("/updateUser")
    public Object updateUser(User user){
        Map<String,Object> message = new HashMap<String, Object>();
        long row = 0;
        try {
            if(user.getPassword() == "" || user.getPassword().length()< 1){
                user.setPassword(null);
            }
            row = userService.updateUser(user);
            if(row > 0){
                message.put("message","ok");
            }else{
                message.put("message","用户修改失败！！！");
            }
        }catch (Exception e){
            e.printStackTrace();
            message.put("error","程序异常");
        }
        return  message;
    }

}
