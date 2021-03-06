package com.seckill.dao;

import com.seckill.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 *
 * @author Darwin
 * @date 2018/5/11
 */
@Mapper
public interface MiaoshaUserDao {

    @Select("SELECT * FROM miaosha_user WHERE id = #{id}")
    MiaoshaUser getById(@Param("id") long id);

    @Update("UPDATE miaosha_user set password = #{password} where id = #{id}")
    void update(MiaoshaUser tobeUpdate);
}
