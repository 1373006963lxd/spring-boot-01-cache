package com.atguigu.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
/*
* 一、搭建基本环境
* 1、导入数据库文件  创建出department和employee表
* 2、创建javaBean封装数据
* 3、整合mybatis操作数据库
*           1.配置数据源信息
*           2.使用注解版的mybatis
*               1）@MapperScan("com.atguigu.cache.mapper")指定需要扫描的接口
* 二、快速体验缓存
*   步骤：
*       1、开启基于注解的缓存 @EnableCaching
*       2.标注缓存注解即可
*           @Cacheable
*           @CacheEvict
*           @CachePut
默认使用的ConcurrentMapCacheManager==ConcurrentManager 将数据保存在ConcurrentMap《object，string》
* 并发中使用缓存中间件：redis，memcached，ehcache
*
* 三、整合redis作为缓存
*虚拟机中拉取docker中的镜像十分慢，加上registry.docker-cn.com/library即可
* docker pull registry.docker-cn.com/library/redis
*
* 1.安装redis，使用docker
* 2.引入redis的starter
* 3.配置redis
* 4.测试缓存
* 原理：CacheManager====cache 缓存组件来实际给缓存中存取数据
* 1）引入redis的starter，容器中保存的是RedisCacheManager
* 2）RedisCacheManager帮我们创建 RedisCache来作为缓存组件，RedisCache通过redis缓存
* 数据
* 3）默认保存数据k-v都是object；利用序列化保存，如何保存为json
*           1）、引入redis的starter，cacheManager变为RedisCacheManager
*           2）、默认创建的RedisCacheManager操作redis的时候使用的是RedisTemplates<Object,Object>
            3)、ReidsTemplate<Object,Object> 是默认使用jdk的序列化机制
            4）、自定义CacheManager
            *
* */


@MapperScan("com.atguigu.cache.mapper")
@SpringBootApplication
@EnableCaching
public class SpringBoot01CacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot01CacheApplication.class, args);
    }

}
