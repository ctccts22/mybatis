package ksmart.mybatis.controller;

import ksmart.mybatis.dto.Goods;
import ksmart.mybatis.dto.Member;
import ksmart.mybatis.service.GoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    private final GoodsService goodsService;

    public  GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @GetMapping("/sellerList")
    public String getGoodsListBySeller(Model model
                                        ,@RequestParam(name="checkSearch", required = false) String[] checkArr
                                        ,@RequestParam(name="searchValue", required = false) String searchValue) {
        List<Member> goodsListBySeller = goodsService.getGoodsListBySeller(checkArr, searchValue);
        model.addAttribute("title", "판매자별상품조회");
        model.addAttribute("goodsListBySeller", goodsListBySeller);
        return "goods/sellerList";
    }

    @GetMapping("/goodsList")
    public String getGoodsList(Model model
                                ,@RequestParam(name="searchKey", required = false) String searchKey
                                ,@RequestParam(name="searchValue", required = false) String searchValue) {
        List<Goods> goodsList = goodsService.getGoodsList(searchKey, searchValue);

        model.addAttribute("title", "상품조회");
        model.addAttribute("goodsList", goodsList);

        return "goods/goodsList";
    }
}
