package com.seckill.dao;

import com.seckill.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 *
 * @author Darwin
 * @date 2018/5/10
 */
@Mapper
public interface UserDao {

    @Select("SELECT * FROM user where id = #{id}")
    User getById(@Param("id") int id);

    @Insert("INSERT INTO user(id, name) values(#{id}, #{name})")
    int insert(User user);

}
