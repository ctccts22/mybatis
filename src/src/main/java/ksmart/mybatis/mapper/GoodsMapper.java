package ksmart.mybatis.mapper;

import ksmart.mybatis.dto.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {

    // 상품목록조회
    public List<Goods> getGoodsList(String searchKey, String searchValue);
}
