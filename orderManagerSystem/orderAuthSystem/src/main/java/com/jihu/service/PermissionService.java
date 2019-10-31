package com.jihu.service;

import com.jihu.dto.MPage;
import com.jihu.entity.Permission;

public interface PermissionService {
    MPage<Permission> getPermissionList(Integer pageIndex, Integer pageSize, String queryText);

    long addPermission(Permission permission);

    Permission getPermissionById(String id);

    long updatePermission(Permission permission);

    long deleteLogicPermission(String ids);
}
