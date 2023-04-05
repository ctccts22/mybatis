package ksmart.mybatis.controller;

import ksmart.mybatis.dto.Goods;
import ksmart.mybatis.dto.Member;
import ksmart.mybatis.dto.MemberLevel;
import ksmart.mybatis.service.GoodsService;
import ksmart.mybatis.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/goods")
public class GoodsController {

    private final GoodsService goodsService;

    @GetMapping("/sellerList")
    public String getGoodsListBySeller(Model model) {
        List<Member> goodsListBySeller = goodsService.getGoodsListBySeller();
        log.info("goodsListBySeller: {}", goodsListBySeller);
        model.addAttribute("title", "판매자별 상품조회");
        model.addAttribute("goodsListBySeller", goodsListBySeller);
        return "goods/sellerList";
    }


    @GetMapping("/goodsList")
    public String getGoodsList(Model model) {
        List<Goods> goodsList = goodsService.getGoodsList();

        log.info("goodsList: {}", goodsList);

        model.addAttribute("title", "상품목록조회");
        model.addAttribute("goodsList", goodsList);

        return "goods/goodsList";
    }

    @GetMapping("/addGoods")
    public String addGoodsForm(Model model) {
        model.addAttribute("title", "상품등록");
        return "goods/addGoods";
    }

    @PostMapping("/addGoods")
    public String addGoods(Goods goods, Model model) {
        boolean isSuccess = goodsService.addGoods(goods);

        if (!isSuccess) {
            model.addAttribute("message", "판매자 아이디가 일치하지 않습니다.");
            return "goods/addGoods";
        } else {
            return "redirect:/goods/goodsList";
        }
    }

}

