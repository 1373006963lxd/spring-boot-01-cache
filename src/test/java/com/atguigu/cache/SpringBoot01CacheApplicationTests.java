package com.atguigu.cache;

import com.atguigu.cache.bean.Employee;
import com.atguigu.cache.mapper.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot01CacheApplicationTests {


    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;//操作都是字符串

    @Autowired
    RedisTemplate redisTemplate;//操作k-v都是对象


    @Autowired
    RedisTemplate<Object, Employee> empredisTemplate;
    /*
    * Redis常见的五种数据类型
    * String ，List ，set  ，Hash ，Zset
    * stringRedisTemplate.opsForValue()操作字符串的
    * stringRedisTemplate.opsForHash()操作Hash
    * stringRedisTemplate.opsForList()操作列表的
    * stringRedisTemplate.opsForZset()操作有序集合
    * stringRedisTemplate.opsForSet()操作集合的
    *
    * 等
    *
    *
    * */

    @Test
    public void test01(){
        /*给redis保存数据*/
        //stringRedisTemplate.opsForValue().append("message","hello");

        /*读数据*/
        String message = stringRedisTemplate.opsForValue().get("message");
        System.out.println(message);//hello world

        /*操作列表 添加*/
/*
        stringRedisTemplate.opsForList().leftPush("list","1");
        stringRedisTemplate.opsForList().leftPush("list","2");*/
    }


    @Test
    public void test02() {
        Employee emp = employeeMapper.getEmpById(1);
        //默认如果保存对象，使用jdk序列化机制，序列化后的数据保存到redis中
    //    redisTemplate.opsForValue().set("emp-01", emp);
        //1.将数据以json的方式保存
        //2.redisTemplate 默认的序列化guiz-----改变默认的序列化规则

        empredisTemplate.opsForValue().set("emp-02",emp);

    }



    @Test
    public void contextLoads() {
        Employee emp = employeeMapper.getEmpById(1);
        System.out.println(emp);
    }

}
