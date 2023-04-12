package ksmart.mybatis.service;

import ksmart.mybatis.dto.Goods;
import ksmart.mybatis.dto.Member;
import ksmart.mybatis.mapper.GoodsMapper;
import ksmart.mybatis.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
public class GoodsService {
    private final GoodsMapper goodsMapper;
    private final MemberMapper memberMapper;

    public GoodsService(GoodsMapper goodsMapper, MemberMapper memberMapper) {
        this.goodsMapper = goodsMapper;
        this.memberMapper = memberMapper;
    }

    public List<Member> getGoodsListBySeller(String[] checkArr, String searchValue){
        Map<String, Object> searchMap = null;

        if(checkArr != null) {
            for (int i=0; i < checkArr.length; i++){
                String searchKey = checkArr[i];
                    switch (searchKey) {
                        case "memberId":
                            searchKey = "m.m_id";
                            break;
                        case "memberEmail":
                            searchKey = "m.m_email";
                            break;
                        default:
                            searchKey = "g.g_name";
                            break;
                    }
                    checkArr[i] = searchKey;
            }
            searchMap = new HashMap<String, Object>();
            searchMap.put("checkArr", checkArr);
            searchMap.put("searchValue", searchValue);
        }
        List<Member> goodsListBySeller = memberMapper.goodsListBySeller(searchMap);
        return goodsListBySeller;
    }

    public List<Goods> getGoodsList(String searchKey, String searchValue){
        if(searchKey != null) {
            switch (searchKey) {
                case "goodsCode":
                    searchKey = "g.g_code";
                    break;
                case "goodsName":
                    searchKey = "g.g_name";
                    break;
                case "goodsPrice":
                    searchKey = "g.g_price";
                    break;
                case "goodsSellerId":
                    searchKey = "g.g_seller_id";
                    break;
                case "memberName":
                    searchKey = "m.m_name";
                    break;
                case "memberEmail":
                    searchKey = "m.m_email";
                    break;
                default:
                    searchKey = "g.g_reg_date";
                    break;
            }
        }
        List<Goods> goodsList = goodsMapper.getGoodsList(searchKey, searchValue);
        return goodsList;
    }
}
