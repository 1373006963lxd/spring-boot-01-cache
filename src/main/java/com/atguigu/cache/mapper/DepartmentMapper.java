package com.atguigu.cache.mapper;

import com.atguigu.cache.bean.Department;
import org.apache.ibatis.annotations.Select;

public interface DepartmentMapper {


    @Select("select * from department where id = #{id}")
    Department getDempById(Integer id);
}
