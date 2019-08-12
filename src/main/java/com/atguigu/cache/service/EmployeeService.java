package com.atguigu.cache.service;

import com.atguigu.cache.bean.Employee;
import com.atguigu.cache.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

/*对缓存注解公用的可以用这个*/
/*@CacheConfig(cacheNames = "emp")*/
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;
/*
* 将方法的运行结果进行缓存，以后要相同的数据，直接从缓存中获取，不用调用方法
* CachManage管理多个cache组件，对缓存的真正CRUD操作在cache组件中，每一个缓存组件有自己唯一一个名字
* 几个属性：
*
*       cacheName/value :指定缓存组件的名字；
*       key：缓存数据使用的key；可以用它来指定。默认是使用方法参数的值 如： 1-方法的返回值
*       keyGenerator： key的生成器；可以自己指定key的生成器的组件id
*               key/keyGenerator 二选一即可
*       cacheManager：指定缓存管理器；  或者cacheResolver 指定获取解析器
*
*       condition：指定符合条件的情况下才缓存
*               condition = " #id>0" 或condition = "#a0>1" 第一个参数>1的时候缓存
*
*       unless: 否定缓存；当unless指定的条件为true，方法的返回值就不会被缓存，可以获取到结果判断
*
*       sync：是否使用异步模式
*
* 运行流程：
* @Cacheable
* 1、方法运行之前，先去查询cache（缓存组件），按照cacheNames的指定的名字获取；
* （cacheManager先获取相应的缓存），第一次获取缓存如果没有cache组件会自动创建。
* 2.去cache中查找缓存的内容，使用一个key，默认就是方法的参数
*   key是按照某种策略生成的；默认是使用keyGenerator生成的，默认使用SimpleKeyGenerator生成key；
* key=getEmp[1]
* 如果没有参数；key=new SimpleKey（）； keyGenerator=“myKeyGenerator”
*           如果有一个参数：key = 参数的值
*           如果有多个参数：key = new Simplekey（params）
* 3、没有查到缓存，就调用目标方法
* 4.将目标方法返回的结果，放进缓存中
*
*
* @Cacheable 标注的方法执行之前先去检查缓存中有没有这个数据，默认按照参数的值作为key去查询缓存，
* 如果没有就运行方法并将结果放入缓存，以后再来调用就可以直接使用缓存中的数据。
*
* 这个注解是先看缓存中有没有，然后决定是否调用方法
* */

    @Cacheable(cacheNames={"emp"}/*,key = "#root.methodName+'['+#id+']'",condition = "#a0>1",unless = "#a0==2"*/)
    public Employee getEmp(Integer id){

        System.out.println("查询"+id+"员工");
        Employee emp = employeeMapper.getEmpById(id);
        return emp;
    }


    /*
    * @CachePut 既调用方法，又更新缓存数据----同步更新缓存
    * 修改了数据库中某个数据，同时更新缓存
    *
    * 这个注解是先调用方法然后将缓存中数据更新
    *
    * 测试步骤：
    * 1.查询1号员工；查到的结果会放到缓存中
    *       key：1 value：lastName：张三
    * 2.以后查询还是之前的结果
    * 3.更新1号员工：【 lastName：zhangsan；gender：0】
    *       将方法的返回值也放进缓存le；
    *       key：传入的employee对象 值返回的employee对象
    * 4.查询1号员工：
    *       应该是更新后的员工：
    *           key="#employee.id",使用传入的参数员工id
    *           key="#result.id" 使用返回后的id
    *           @CacheAble 的key 是不能用#result
    * 为什么是没有更新的数据呢？  1号员工没有在缓存中更行---因为key不同导致的
    * */
    @CachePut(value = "emp",key = "#result.id")
    public Employee updateEmp(Employee employee){
        System.out.println("updateEmp"+employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }
    /*
    * @CacheEvict  缓存清除----删除缓存中的数据
    *allEntries = true :指定清除这个缓存中所有的数据
    *
    * beforeInvocation = false 缓存的清除是否在方法之前执行，默认代表是在方法执行之后执行
    * 即：先处理方法再清除
    *如果方法中存在错误，则缓存中清除数据失败。
    * beforeInvocation = true,则相反
    *
    * */
    @CacheEvict(value = "emp",key = "#id",beforeInvocation = true)
    public void deleteEmp(Integer id){
        System.out.println("deleteEemp"+id);
        /*上面注解就是对缓存里面的操作*/

        /*  下面是对数据库的操作，清除数据库*/
        //employeeMapper.deleteEempById(id);
//           int i = 10/0;
    }
/*
* 根据lastname查询数据后，则缓存中就有多种存放方式
* 按lastName存放的
* 按id存放的
* 按email存放的
*
*
* ------------------------------------------定义复杂的缓存规则
*
* */

    @Caching(
            cacheable = {
                    @Cacheable(value = "emp",key = "#lastName")
            },

            put = {
                    @CachePut(value = "emp",key = "#result.id"),
                    @CachePut(value = "emp",key = "#result.email")
            }

    )
    public Employee getEmpBylastName(String lastName){
        Employee employee = employeeMapper.getEmpBylastName(lastName);
        return employee;
    }
}
