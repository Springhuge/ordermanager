package com.jihu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jihu.Mapper.PermissionMapper;
import com.jihu.dto.MPage;
import com.jihu.entity.Permission;
import com.jihu.service.PermissionService;
import com.jihu.util.MyStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.expression.Ids;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MPage<Permission> getPermissionList(Integer pageIndex, Integer pageSize, String queryText) {
        MPage<Permission> permissionList = new MPage<Permission>();

        Page<Permission> page = new Page<Permission>(pageIndex,pageSize,true);

        IPage<Permission> ipage ;
        if(StringUtils.isNotEmpty(queryText)){
            ipage = permissionMapper.selectPage(page,new LambdaQueryWrapper<Permission>()
                .eq(Permission::getDeleted,0)
                .and(q ->q.like(Permission::getPermission,queryText).or().like(Permission::getDescription,queryText))
                .orderByAsc(Permission::getPermission));
        }else{
            ipage = permissionMapper.selectPage(page,new LambdaQueryWrapper<Permission>()
                    .eq(Permission::getDeleted,0)
                    .orderByAsc(Permission::getPermission));
        }
        permissionList.setLists(ipage.getRecords());
        permissionList.setPages(ipage.getPages());
        permissionList.setTotal(ipage.getTotal());
        return permissionList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long addPermission(Permission permission) {
        permission.setDeleted(0);
        long row = permissionMapper.insert(permission);
        return row;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Permission getPermissionById(String id) {
        Permission permission = permissionMapper.selectOne(new LambdaQueryWrapper<Permission>()
            .eq(Permission::getDeleted,0)
            .eq(Permission::getId,id));
        return permission;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long updatePermission(Permission permission) {
        long row = permissionMapper.updateById(permission);
        return row;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long deleteLogicPermission(String ids) {
        long row = 0;
        Permission permission = null;
        if(ids.indexOf(",") == -1){
            permission = new Permission();
            permission.setDeleted(1);
            permission.setId(ids);
            row = permissionMapper.updateById(permission);
        }else{
            String [] onece = MyStringUtils.getids(ids);
            for (String id: onece) {
                row++;
                permission = new Permission();
                permission.setId(id);
                permission.setDeleted(1);
                permissionMapper.updateById(permission);
            }
        }
        return row;
    }
}
