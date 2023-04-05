package ksmart.mybatis.mapper;

import ksmart.mybatis.dto.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {
    List<Goods> getGoodsList();
    void insertGoods(Goods goods);

}