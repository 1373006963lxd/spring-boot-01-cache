package com.atguigu.cache.mapper;

import com.atguigu.cache.bean.Employee;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

/*private Integer id;
private String lastName;
private String email;
private Integer gender; //性别 1男  0女
private Integer dId;*/
@Mapper
public interface EmployeeMapper {

    /*
    * 通过传来的id然后调用select来查询数据库中的信息
    * */
    @Select("select * from employee where id=#{id}")
    public Employee getEmpById(Integer id);




    /*
    * 前面是  数据库中的字段名=javabean里面的属性名
    * 传进来一个员工信息，然后调用Update来更新数据库的信息
    * */
    @Update("update employee set lastName=#{lastName},email=#{email},gender=#{gender},d_id=#{dId} where id=#{id}")
    public void updateEmp(Employee employee);



    @Delete("delete from employee where id=#{id}")
    public void deleteEempById(Integer id);


    /*
    * 数据库中的信息======对象中属性的值
    * */
    @Insert("insert into employee(lastName,email,gender,d_id) values(#{lastName}," +
            "#{email},#{gender},#{dId})")
    public void insertEmp(Employee employee);



    /*
    * 通过lastName查询用户信息
    * */
    @Select("select * from employee where lastName=#{lastName}")
    public Employee getEmpBylastName(String lastName);
}
