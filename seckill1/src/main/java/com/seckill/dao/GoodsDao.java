package com.seckill.dao;

import com.seckill.domain.Goods;
import com.seckill.domain.MiaoshaGoods;
import com.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 *
 * @author Darwin
 * @date 2018/5/14
 */
@Mapper
public interface GoodsDao {

    @Select("select g.*, mg.miaosha_price, mg.stock_count, mg.start_date, mg.end_date " +
            "from miaosha_goods mg " +
            "left join goods g on mg.goods_id = g.id")
    List<GoodsVo> listGoodsVo();

    @Select("select  g.*, mg.miaosha_price, mg.stock_count, mg.start_date, mg.end_date " +
            "from miaosha_goods mg " +
            "left join goods g on mg.goods_id = g.id " +
            "where g.id = #{goodsId}")
    GoodsVo getGoodsVoById(long goodsId);

    @Update("update miaosha_goods set stock_count = stock_count-1 where goods_id = #{goodsId}")
    int reduceStock(MiaoshaGoods g);
}