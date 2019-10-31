package com.jihu.service;

import com.jihu.dto.MPage;
import com.jihu.entity.Role;

public interface RoleService {

    MPage<Role> getRoleList(Integer pageIndex, Integer pageSize, String queryText);

    long addRole(Role role);

    long removeLogicRoles(String ids);

    Role getRoleById(String id);

    long updateRole(Role role);
}
