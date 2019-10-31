package com.jihu;

import com.jihu.Mapper.UserMapper;
import com.jihu.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InsertTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 获取到UUid
     */
    @Test
    public void testUUid(){
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());
        System.out.println(UUID.randomUUID().toString());
    }

    /**
     * 插入1000数据测试
     */
    @Test
    public void insert(){
        int i  =  3;
        int rows = 0;
        User user = new User();
        while ( i <= 1000 ){
            user.setId(UUID.randomUUID().toString());
            user.setUsername("test"+i+"");
            user.setPassword("123456");
            user.setDeleted(0);
            rows = userMapper.insert(user);
            i++;
        }
        System.out.println(rows);
    }


}
