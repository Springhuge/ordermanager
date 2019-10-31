package com.jihu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jihu.Mapper.RoleMapper;
import com.jihu.Mapper.UserMapper;
import com.jihu.dto.MPage;
import com.jihu.entity.Role;
import com.jihu.service.RoleService;
import com.jihu.util.MyStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public MPage<Role> getRoleList(Integer pageIndex, Integer pageSize, String queryText) {
        MPage<Role> roleList = new MPage<>();
        Page<Role> page = new Page<Role>(pageIndex,pageSize,true);

        IPage<Role> roles;
        if(StringUtils.isNotEmpty(queryText)){
            roles = roleMapper.selectPage(page,new LambdaQueryWrapper<Role>()
                    .eq(Role::getDeleted,0)
                    .and(q ->q.like(Role::getRole,queryText).or().like(Role::getDescription,queryText))
                    .orderByAsc(Role::getRole));
        }else{
            roles = roleMapper.selectPage(page,new LambdaQueryWrapper<Role>()
                .eq(Role::getDeleted,0)
                .orderByAsc(Role::getRole));
        }
        roleList.setLists(roles.getRecords());
        roleList.setTotal(roles.getTotal());
        roleList.setPages(roles.getPages());
        return roleList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long addRole(Role role) {
        role.setDeleted(0);
        long row  = roleMapper.insert(role);
        return row;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long removeLogicRoles(String ids) {
        long row = 0;
        Role role = null;
        if(ids.indexOf(",") == -1){
            role = new Role();
            role.setId(ids);
            role.setDeleted(1);
            row = roleMapper.updateById(role);
        }else{
            String[] oneceId = MyStringUtils.getids(ids);
            for (String id:oneceId) {
                row++;
                role = new Role();
                role.setId(id);
                role.setDeleted(1);
                roleMapper.updateById(role);
            }
        }
        return row;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role getRoleById(String id) {
        Role role = roleMapper.selectOne(new LambdaQueryWrapper<Role>()
            .eq(Role::getDeleted,0)
            .eq(Role::getId,id));
        return role;
    }

    @Override
    public long updateRole(Role role) {
        long row = roleMapper.updateById(role);
        return row;
    }
}
