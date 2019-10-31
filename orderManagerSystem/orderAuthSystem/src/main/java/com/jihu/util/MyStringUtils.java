package com.jihu.util;

/**
 * String 工具类
 * @author jihu
 */
public class MyStringUtils {


    //将前端传过来的id进行解析成单个id
    public static String[] getids(String ids){
        String[] oneceId = ids.split(",");
        return  oneceId;
    }


}
