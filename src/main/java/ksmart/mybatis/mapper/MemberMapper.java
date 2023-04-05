package ksmart.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import ksmart.mybatis.dto.Member;
import ksmart.mybatis.dto.MemberLevel;

@Mapper
public interface MemberMapper {

	public void deleteLoginRecordsByMemberId(String memberId);
	public void deleteGoodsRecordsByMemberId(String memberId);
	public void deleteOrderRecordsByMemberId(String memberId);
	public void deleteOrdersForMember(String memberId);
	public void deleteMember(String memberId);


//	public int deleteMember(String memberId);


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
