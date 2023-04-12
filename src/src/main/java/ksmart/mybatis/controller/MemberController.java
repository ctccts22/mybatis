package ksmart.mybatis.controller;

import jakarta.servlet.http.HttpSession;
import ksmart.mybatis.dto.Member;
import ksmart.mybatis.dto.MemberLevel;
import ksmart.mybatis.mapper.MemberMapper;
import ksmart.mybatis.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {

    private static final Logger log = LoggerFactory.getLogger(MemberController.class);

    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @PostMapping("/login")
    public String login(@RequestParam(name = "memberId") String memberId
                       , @RequestParam(name = "memberPw") String memberPw
                       , HttpSession session
                       , RedirectAttributes reAttr) {
        String redirect = "redirect:/member/login";

        return redirect;

    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "로그인");

        return "login/login";
    }

    public MemberController(MemberService memberService, MemberMapper memberMapper) {
        this.memberService = memberService;
        this.memberMapper = memberMapper;
    }

    @PostMapping("removeMember")
    public String removeMember(@RequestParam(name = "memberId") String memberId
                                ,@RequestParam(name = "memberPw") String memberPw) {
        String redirectURI = "redirect:/member/removeMember?memberId=" + memberId;
        //비밀번호 확인
        Member member = memberService.getMemberInfoById(memberId);
        if(member != null) {
            String checkPw = member.getMemberPw();
            if(checkPw.equals(memberPw)) {
                //서비스 호출
                memberService.removeMember(memberId);
                redirectURI = "redirect:/member/memberList";
            }
        }
        return redirectURI;
    }

    /**
     * 회원탈퇴화면
     * @param memberId
     * @param model
     * @return
     */
    @GetMapping("/removeMember")
    public String removeMember(@RequestParam(name = "memberId") String memberId, Model model){
        model.addAttribute("title", "회원탈퇴");
        model.addAttribute("memberId", memberId);

        return "member/removeMember";
    }

    /**
     * 회원정보수정
     * @param member
     * @return
     */
    @PostMapping("/modifyMember")
    public String modifyMember(Member member) {
        memberMapper.modifyMember(member);

        return "redirect:/member/memberList";
    }

    /**
     * 회원수정화면
     * @param memberId
     * @param model
     * @return
     */
    @GetMapping("/modifyMember")
    public String modifyMember(@RequestParam(name="memberId") String memberId, Model model) {
        Member memberInfo = memberService.getMemberInfoById(memberId);
        List<MemberLevel> memberLevelList = memberService.getMemberLevelList();
        model.addAttribute("title", "회원수정");
        model.addAttribute("memberLevelList", memberLevelList);
        model.addAttribute("memberInfo", memberInfo);

        return "/member/modifyMember";
    }


    /**
     * 커맨드객체: 클라이언트 쿼리파라미터 요청시 name의 속성이 dto의 멤버변수명과 일치하면 자동 바인딩
     * @param member <- 커맨드 객체
     * @return String redirect 키워드: 리디렉션 요청
     * 문법 : redirect:요청주소;
     */
    @PostMapping("/addMember")
    public String addMember(Member member) {
        log.info("화면에서 전달받은 데이터 : {}", member);
        memberService.addMember(member);
        return "redirect:/member/memberList";
    }

    @PostMapping("/idCheck")
    @ResponseBody
    public boolean idCheck(@RequestParam(name = "memberId") String memberId) {
        boolean checked = true;
        //아이디 중복체크
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
    public String getMemberList(Model model
                                ,@RequestParam(name="searchKey", required = false) String searchKey
                                ,@RequestParam(name="searchValue", required = false) String searchValue) {
        List<Member> memberList = memberService.getMemberList(searchKey, searchValue);
        model.addAttribute("title", "회원목록조회");
        model.addAttribute("memberList", memberList);

        return "member/memberList";
    }
}
