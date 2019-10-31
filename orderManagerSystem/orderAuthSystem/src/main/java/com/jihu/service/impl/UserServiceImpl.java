package com.jihu.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jihu.Mapper.UserMapper;
import com.jihu.dto.MPage;
import com.jihu.entity.User;
import com.jihu.service.UserService;
import com.jihu.util.MyStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    //登录
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean queryLogin(User user) {
        User rgUser = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername,user.getUsername())
                .eq(User::getPassword,user.getPassword()));
        if(rgUser != null){
            return true;
        }
        return false;
    }

    //分页
    @Override
    @Transactional(rollbackFor = Exception.class)
    //@Cacheable(value="UserListCache",key="'user.getAllUsers'")
    public MPage<User> getAllUsers(Integer pageIndex, Integer pageSize,String queryText) {

        Page<User> page = new Page<User>(pageIndex,pageSize,true);
        IPage<User> iPage;
        if(StringUtils.isNotEmpty(queryText)){
            iPage = userMapper.selectPage(page,new LambdaQueryWrapper<User>()
                    .like(User::getUsername,queryText)
                    .eq(User::getDeleted,0)
                    .orderByDesc(User::getUsername));
        }else{
            iPage = userMapper.selectPage(page,new LambdaQueryWrapper<User>()
                    .eq(User::getDeleted,0)
                    .orderByAsc(User::getUsername));
        }
        MPage<User> mpage =  new MPage<User>();
        mpage.setLists(iPage.getRecords());
        mpage.setPages(iPage.getPages());
        mpage.setTotal(iPage.getTotal());

        return mpage;
    }

    //插入用户
    @Override
    @Transactional(rollbackFor = Exception.class)
    public long insertUser(User user) {
        long row = 0;
        if(user == null){
            return row;
        }
        user.setDeleted(0);
        row = userMapper.insert(user);
        return row;
    }

    //物理删除接口
    @Override
    @Transactional(rollbackFor = Exception.class)
    public long removeUsers(String ids) {
        long row = 0 ;
        if(ids.indexOf(",")==-1){
            row = userMapper.deleteById(ids);
        }else{
           String[] oneceId = MyStringUtils.getids(ids);
           for (String id:oneceId) {
               row++;
               userMapper.deleteById(id);
           }
        }
        return row;
    }

    //逻辑删除接口
    @Override
    @Transactional(rollbackFor = Exception.class)
    public long removeLogicUsers(String ids) {
        long row = 0 ;
        User user = null;
        if(ids.indexOf(",") == -1){
            user = new User();
            user.setId(ids);
            user.setDeleted(1);
            row = userMapper.updateById(user);
        }else{
            String[] oneceId = MyStringUtils.getids(ids);
            for (String id:oneceId) {
                row++;
                user = new User();
                user.setId(id);
                user.setDeleted(1);
                userMapper.updateById(user);
            }
        }
        return row;
    }

    /**
     * 根据id获取逻辑未删除的所有
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public User getUserById(String id) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
            .eq(User::getId,id)
            .eq(User::getDeleted,0)
            .select(User.class,i ->!i.getColumn().equals("password")));
        return user;
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public long updateUser(User user) {
        long row = userMapper.updateById(user);
        return row;
    }
}
