package ksmart.mybatis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import ksmart.mybatis.dto.Goods;
import ksmart.mybatis.dto.Member;
import ksmart.mybatis.mapper.GoodsMapper;
import ksmart.mybatis.mapper.MemberMapper;
import ksmart.mybatis.service.GoodsService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/goods")
@Slf4j
public class GoodsController {

	private final GoodsService goodsService;
	private final MemberMapper memberMapper;
	private final GoodsMapper goodsMapper;


	public GoodsController(GoodsService goodsService, MemberMapper memberMapper, GoodsMapper goodsMapper) {
		this.goodsService = goodsService;
		this.goodsMapper = goodsMapper;
		this.memberMapper = memberMapper;
	}
	
	@DeleteMapping("/removeGoods") //등록 -> 삭제 html에서는 get post만 지원함
	// request.getparameter 상품코드, 아이디, 비밀번호 파라미터를 요청한다.
	public String removeGoods(@RequestParam(name="goodsCode") String goodsCode
							 ,@RequestParam(name="memberId") String memberId
							 ,@RequestParam(name="memberPw") String memberPw
							 ,HttpSession session // 세션 사용하기위해 호출
							 ,RedirectAttributes reAttr) { // 리다이렉트 -> goodsList js
		String memberLevel = (String) session.getAttribute("SLEVEL");
		// memberLevel은 int이므로 String 타입으로 다운캐스팅(형변환), 왜냐면 session <String, Object> 이기 때문에..
		// SLEVEL 세션값을 가져온다.
		// 가져온 세션값은 타임리프 서바시사이드 연결사용 -> 자동형변경해줌 개꿀
		boolean isDelete = true;
		String msg = "";
		if(memberLevel != null && "2".equals(memberLevel)) {
			// And 둘다 맞아야 true
			isDelete = goodsMapper.isSellerByGoodsCode(memberId, goodsCode);
		}
		
		Member member = memberMapper.getMemberInfoById(memberId);
		if(member != null) {
			String checkPw = member.getMemberPw();
			if(!checkPw.equals(memberPw)) isDelete = false;
		}
		if(isDelete) {
			goodsService.removeGoods(goodsCode);
			msg = "상품코드: "+ goodsCode + " 가 삭제되었습니다.";
		}else {
			msg = "상품코드: "+ goodsCode + " 가 삭제할 수 없습니다.";			
		}
		reAttr.addAttribute("msg", msg);
		
		return "redirect:/goods/goodsList";
	}

	@GetMapping("/removeGoods") // 조회 goods/removeGoods
	public String removeGoods(Model model
							 ,@RequestParam(name="goodsCode") String goodsCode) {
		model.addAttribute("title", "상품삭제");
		model.addAttribute("goodsCode", goodsCode);
		return "goods/removeGoods";
	}
	
	@PutMapping("/modifyGoods")
	public String modifyGoods(Goods goods) {
		goodsService.modifyGoods(goods);
		return "redirect:/goods/goodsList";
	}
	
	@GetMapping("/modifyGoods")
	public String modifyGoods(Model model
							 ,@RequestParam(name="goodsCode") String goodsCode) {
		
		Goods goodsInfo = goodsService.getGoodsInfoByCode(goodsCode);
		
		model.addAttribute("title", "상품수정");
		model.addAttribute("goodsInfo", goodsInfo);
		
		return "goods/modifyGoods";
	}
	
	@PostMapping("/addGoods")
	public String addGoods(Goods goods) {
		goodsService.addGoods(goods);
		return "redirect:/goods/goodsList";
	}
	
	@PostMapping("/sellersInfo")
	@ResponseBody
	public List<Member> sellersInfo(){
		String searchKey = "m.m_level";
		String searchValue = "2";
		List<Member> memberList = memberMapper.getMemberList(searchKey, searchValue);
		memberList.forEach(seller -> seller.setMemberPw(""));
		return memberList;
	}

	@GetMapping("/addGoods")
	public String addGoods(Model model) {
		
		model.addAttribute("title", "상품등록");
		
		return "goods/addGoods";
	}
		
	@GetMapping("/sellerList")
	public String getGoodsListBySeller( Model model
									   ,@RequestParam(name="checkSearch", required = false) String[] checkArr
									   ,@RequestParam(name="searchValue", required = false) String searchValue) {
		
		List<Member> goodsListBySeller = goodsService.getGoodsListBySeller(checkArr, searchValue);
		model.addAttribute("title", "판매자별상품조회");
		model.addAttribute("goodsListBySeller", goodsListBySeller);
		
		return "goods/sellerList";
	}
	
	@GetMapping("/goodsList")
	public String getGoodsList(Model model
			   				  ,HttpSession session
			   				  ,@RequestParam(name="msg", required = false) String msg) {
		String memberLevel = (String) session.getAttribute("SLEVEL");
		Map<String, Object> paramMap = null;
		if(memberLevel != null && "2".equals(memberLevel)) {
			String sellerId = (String)session.getAttribute("SID");
			
			paramMap = new HashMap<String, Object>();
			paramMap.put("searchKey", "g_seller_id");
			paramMap.put("searchValue", sellerId);
		}
		
		List<Goods> goodsList = goodsService.getGoodsList(paramMap);
		
		model.addAttribute("title", "상품조회");
		model.addAttribute("goodsList", goodsList);
		if(msg != null) model.addAttribute("msg", msg);
		
		return "goods/goodsList";
	}
}
