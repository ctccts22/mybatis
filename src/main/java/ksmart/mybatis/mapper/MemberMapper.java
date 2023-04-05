package ksmart.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import ksmart.mybatis.dto.Member;
import ksmart.mybatis.dto.MemberLevel;

@Mapper
public interface MemberMapper {
	//판매자별 상품조회
	List<Member> goodsListBySeller();

	Member findMemberById(String memberId);

	// 판매자가 등록한 상품 주문 이력 삭제
	public int deleteOrderBySellerId(String memberId);

	// 판매자가 등록한 상품삭제
	public int deleteGoodsBySellerId(String memberId);

	// 구매자가 주문한 이력 삭제
	public int deleteOrderById(String memberId);

	// 로그인 이력 삭제
	public int deleteLoginById(String memberId);

	// 회원 탈퇴
	public int deleteMemberById(String memberId);


	// 회원 수정
	public int modifyMember(Member member);
	public Member getMemberInfoById(String memberId);

	//회원가입
	public int addMember(Member member);

	//회원아이디 중복체크
	public boolean idCheck(String memberId);
	// 회원의 목록 조회
	public List<Member> getMemberList();
	// 회원등급 조회
	public List<MemberLevel> getMemberLevelList();


}
