package ksmart.mybatis.service;

import ksmart.mybatis.dto.Goods;
import ksmart.mybatis.dto.Member;
import ksmart.mybatis.mapper.GoodsMapper;
import ksmart.mybatis.mapper.MemberMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class GoodsService {

    private final GoodsMapper goodsMapper;
    private final MemberMapper memberMapper;

    public List<Member> getGoodsListBySeller() {
        return memberMapper.goodsListBySeller();
    }

    public List<Goods> getGoodsList() {
        return goodsMapper.getGoodsList();
    }
    public boolean addGoods(Goods goods) {
        Member member = memberMapper.findMemberById(goods.getGoodsSellerId());
        log.info("Goods seller ID: {}", goods.getGoodsSellerId());
        log.info("Found member: {}", member);
        if (member == null) {
            return false;
        } else {
            goods.setGoodsRegDate(LocalDateTime.now());
            goodsMapper.insertGoods(goods);
            return true;
        }
    }
}