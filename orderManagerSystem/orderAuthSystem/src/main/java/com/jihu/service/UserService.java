package com.jihu.service;

import com.jihu.dto.MPage;
import com.jihu.entity.User;

public interface UserService {


    boolean queryLogin(User user);

    MPage<User> getAllUsers(Integer pageIndex, Integer pageSize,String queryText);

    long insertUser(User user);

    long removeUsers(String ids);

    long removeLogicUsers(String ids);

    User getUserById(String id);

    long updateUser(User user);
}
