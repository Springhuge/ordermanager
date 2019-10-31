package com.jihu.controller;

import com.jihu.Mapper.RoleMapper;
import com.jihu.dto.MPage;
import com.jihu.entity.Role;
import com.jihu.service.RoleService;
import com.jihu.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 角色控制器
 * @author jihu
 */
@RestController
@RequestMapping("/role")
@Api(tags = "角色控制器")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "角色列表接口",notes = "获取角色列表")
    @PostMapping("/getRoleList")
    public Object getRoleList(@RequestParam(value = "pageIndex",required = false,defaultValue = "1") Integer pageIndex,
                             @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize
            ,String queryText){
        Map<String,Object> message = new HashMap<String,Object>();

        try {
            MPage<Role> pages =roleService.getRoleList(pageIndex,pageSize,queryText);
            message.put("page",pages);
            message.put("message","ok");
        }catch (Exception e){
            e.printStackTrace();
            message.put("error","程序异常");
        }
        return message;
    }

    @ApiOperation(value = "添加角色名接口",notes = "添加角色")
    @PostMapping("/addRole")
    public Object addRole(Role role){
        Map<String,Object> message = new HashMap<String, Object>();

        try {
            long row = roleService.addRole(role);
            if(row > 0){
                message.put("message","ok");
            }else {
                message.put("message","添加角色失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            message.put("error","程序异常");
        }
        return message;
    }

    @ApiOperation(value = "角色逻辑删除接口",notes = "角色逻辑删除")
    @PostMapping("/deleteLogicRoles")
    public Object deleteLogicRoles(String ids){
        Map<String,Object> message = new HashMap<String,Object>();

        try {
            long row = roleService.removeLogicRoles(ids);
            if(row > 0){
                message.put("message","ok");
            }else {
                message.put("message","删除失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            message.put("error","程序异常！！！");
        }
        return message;
    }

    @ApiOperation(value = "获取权限信息接口",notes = "根据id获取权限信息")
    @GetMapping("/getRoleById")
    public Object getRoleById(String id){
        Map<String,Object> message = new HashMap<String, Object>();

        try {
            Role role  = roleService.getRoleById(id);
            if(role != null){
                message.put("role",role);
                message.put("message","ok");
            }else{
                message.put("message","权限不存在");
            }
        }catch (Exception e){
            e.printStackTrace();
            message.put("error","程序异常！！！");
        }
        return message;
    }

    @ApiOperation(value = "修改角色接口",notes = "根据id修改角色")
    @PutMapping("/updateRole")
    public Object updateRole(Role role){
        Map<String,Object> message = new HashMap<String,Object>();

        try {
            long row = roleService.updateRole(role);
            if(row > 0){
                message.put("message","ok");
            }else {
                message.put("message","修改失败!!!");
            }
        }catch (Exception e){
            e.printStackTrace();
            message.put("error","程序异常！！！");
        }
        return message;
    }



}
