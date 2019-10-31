#开发项目中遇到的异常

1. DefaultSerializer requires a Serializable payload but received an object of type
    在使用Redis做缓存的时候，要缓存的对象需要实现序列化接口，因为在使用缓存的时候，spring会先将对象进行序列化在存入Redis;
    
2.请求response中含有password字段
    解决方案：
        mybatisplus:将password在查询字段移除
        mybatis:不查询password