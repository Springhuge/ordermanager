package com.jihu.controller;

import com.jihu.dto.MPage;
import com.jihu.entity.Permission;
import com.jihu.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "许可控制器")
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "许可列表接口",notes = "获取许可列表")
    @PostMapping("/getPermissionList")
    public Object getPermissionList(@RequestParam(value = "pageIndex",required = false,defaultValue = "1") Integer pageIndex,
                                    @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize
            ,String queryText){
        Map<String,Object> message = new HashMap<String,Object>();

        try {
            MPage<Permission> pages= permissionService.getPermissionList(pageIndex,pageSize,queryText);
            message.put("page",pages);
            message.put("message","ok");
        }catch (Exception e){
            e.printStackTrace();
            message.put("error","程序异常！！！");
        }
        return message;
    }

    @ApiOperation(value = "添加许可接口",notes = "添加许可")
    @PostMapping("/addPermission")
    public Object addPermission(Permission permission){
        Map<String,Object> message = new HashMap<>();

        try {
            long row = permissionService.addPermission(permission);
            if(row > 0){
                message.put("message","ok");
            }else{
                message.put("message","添加失败！！！");
            }
        }catch (Exception e){
            e.printStackTrace();
            message.put("error","程序异常！！！");
        }
        return message;
    }

    @ApiOperation(value = "获取许可信息",notes = "根据id获取许可信息")
    @GetMapping("/getPermissionById")
    public Object getPermissionById(String id){
        Map<String,Object> message = new HashMap<String,Object>();

        try {
            Permission permission = permissionService.getPermissionById(id);
            if(permission != null){
                message.put("message","ok");
                message.put("permission",permission);
            }else{
                message.put("message","许可不存在！！！");
            }
        }catch (Exception e){
            e.printStackTrace();
            message.put("error","程序异常！！！");
        }
        return message;
    }

    @ApiOperation(value = "修改许可接口",notes = "修改许可")
    @PutMapping("/updatePermission")
    public Object updatePermission(Permission permission){
        Map<String,Object> message = new HashMap<String, Object>();

        try {
            long row = permissionService.updatePermission(permission);
            if(row > 0){
                message.put("message","ok");
            }else{
                message.put("message","修改失败！！！");
            }
        }catch (Exception e){
            e.printStackTrace();
            message.put("error","程序异常！！！");
        }
        return  message;
    }

    @ApiOperation(value = "逻辑删除接口",notes = "逻辑删除")
    @PostMapping("/deleteLogicPermissions")
    public Object deleteLogicPermissions(String ids){
       Map<String,Object> messgae = new HashMap<String, Object>();

       try {
           long row = permissionService.deleteLogicPermission(ids);
           if(row > 0){
               messgae.put("message","ok");
           }else{
               messgae.put("message","删除失败!!!");
           }
       }catch (Exception e){
           e.printStackTrace();
           messgae.put("message","程序异常！！！");
       }
       return messgae;
    }
}
