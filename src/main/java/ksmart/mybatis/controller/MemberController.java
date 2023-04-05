package ksmart.mybatis.controller;

import java.util.List;

import ksmart.mybatis.mapper.MemberMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ksmart.mybatis.dto.Member;
import ksmart.mybatis.dto.MemberLevel;
import ksmart.mybatis.service.MemberService;

@Controller
@AllArgsConstructor
@RequestMapping("/member")
public class MemberController {


	private static final Logger log = LoggerFactory.getLogger(MemberController.class);


	private final MemberService memberService;
	private final MemberMapper memberMapper;

	/**
	 * 회원탈퇴화면
	 * @param memberId
	 * @param model
	 * @return
	 */
	@GetMapping("/deleteMember")
	public String deleteMember(@RequestParam("memberId") String memberId,
							   Model model) {
		model.addAttribute("title", "회원탈퇴");
		model.addAttribute("memberId", memberId);

//		memberService.deleteMember(memberId);

		return "member/deleteMember";
	}
	@PostMapping("/deleteMember")
	public String deleteMember(@RequestParam("memberId") String memberId,
							   @RequestParam("memberPw") String memberPw,
							   Model model) {
		if (memberService.isPasswordCorrect(memberId, memberPw)) {
			memberService.deleteOrdersForMember(memberId);
			memberService.deleteMember(memberId);
			return "redirect:/member/memberList";
		} else {
			model.addAttribute("title", "회원탈퇴");
			model.addAttribute("memberId", memberId);
			model.addAttribute("errorMessage", "비밀번호가 올바르지 않습니다.");
			return "member/deleteMember";
		}
	}

	// update
	@GetMapping("/modifyMember")
	public String ModifyMember(@RequestParam("memberId") String memberId
			, Model model) {
		Member memberInfo = memberService.getMemberInfoById(memberId);
		List<MemberLevel> memberLevelList = memberService.getMemberLevelList();

		model.addAttribute("title", "회원수정");
		model.addAttribute("memberInfo", memberInfo);
		model.addAttribute("memberLevelList", memberLevelList);

		return "member/modifyMember";
	}
	@PostMapping("/modifyMember")
	public String modifyMember(@ModelAttribute Member member) {
		memberService.modifyMember(member);
		log.info("화면에서 전달 받은 데이터 : {}", member);
		return "redirect:/member/memberList";
	}

//	@PostMapping("/modifyMember")
//	public String modifyMember(Member member, Model model) {
//		memberService.modifyMember(member);
//		log.info("화면에서 전달 받은 데이터 : {}", member);
//		model.addAttribute("member", member);
//		return "redirect:/member/memberList";
//	}
// =>
// <form id="modifyMemberForm" th:action="@{/member/modifyMember}" th:object="${member}" method="post">



	/**
	 * 커맨드객체 클라이언트 쿼리파라미터 요청시 name의 속성이
	 * 			DTO의 맴버변수명과 일치하면 자동 바인딩
	 * @param member <- 커맨드객체
	 * @return String redirect 키워드 리다이렉션 요청
	 * 문법 : redirect: 요청주소;
	 */

	@PostMapping("/addMember")
	public String addMember(Member member) {
		memberService.addMember(member);
		log.info("화면에서 전달 받은 데이터 : {}", member);
		return "redirect:/member/memberList";
	}

	@PostMapping("/idCheck")
	@ResponseBody
	public boolean idCheck(@RequestParam(name = "memberId") String memberId) {
		boolean checked = true;
		// 아이디 중복체크
		checked = memberMapper.idCheck(memberId);

		return checked;
	}

	@GetMapping("/addMember")
	public String addMember(Model model) {
		
		List<MemberLevel> memberLevelList = memberService.getMemberLevelList();
		
		model.addAttribute("title", "회원가입");
		model.addAttribute("memberLevelList", memberLevelList);
		
		return "member/addMember";
	}
	
	/**
	 * http://localhost/member/memberList
	 * @param model
	 * @return
	 */
	@GetMapping("/memberList")
	public String getMemberList(Model model) {
		List<Member> memberList = memberService.getMemberList();
		
		log.info("memberList: {}", memberList);
		
		model.addAttribute("title", "회원목록조회");
		model.addAttribute("memberList", memberList);
		
		return "member/memberList";
	}
	
	
	
	
	
	
}
