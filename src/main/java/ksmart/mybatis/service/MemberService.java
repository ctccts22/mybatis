package ksmart.mybatis.service;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ksmart.mybatis.dto.Member;
import ksmart.mybatis.dto.MemberLevel;
import ksmart.mybatis.mapper.MemberMapper;

// @Transactional 클래스 가지고 있는 메소드 전체에 트랜잭션 처리
@Service
@Transactional
@AllArgsConstructor
public class MemberService {

	// DI 생성자 메소드 주입방식
	private final MemberMapper memberMapper;

	public void deleteMember(String memberId) {
		memberMapper.deleteLoginRecordsByMemberId(memberId);
		memberMapper.deleteOrderRecordsByMemberId(memberId);
		memberMapper.deleteGoodsRecordsByMemberId(memberId);
		memberMapper.deleteMember(memberId);
	}
	public void deleteOrdersForMember(String memberId) {
		memberMapper.deleteOrdersForMember(memberId);
	}

	public boolean isPasswordCorrect(String memberId, String memberPw) {
		Member member = memberMapper.getMemberInfoById(memberId);
		return member != null && member.getMemberPw().equals(memberPw);
	}

	//delete
//	public void deleteMember(String memberId) {
//		memberMapper.deleteMember(memberId);
//	}

	// update
	public Member getMemberInfoById(String memberId) {
		Member memberInfo = memberMapper.getMemberInfoById(memberId);
		return memberInfo;
	}
	public int modifyMember(Member member) {
		int result = memberMapper.modifyMember(member);
		return result;
	}

	public int addMember(Member member) {
		int result = memberMapper.addMember(member);
		return result;
	}
	/**
	 * 회원 등급 조회
	 * @return List<MemberLevel>
	 */
	public List<MemberLevel> getMemberLevelList(){
		
		List<MemberLevel> memberLevelList = memberMapper.getMemberLevelList();
		
		return memberLevelList;
	}
	
	/**
	 * 회원목록 조회
	 * @return List<Member>
	 */
	public List<Member> getMemberList(){
		List<Member> memberList = memberMapper.getMemberList();
		if(memberList != null) {	
			/*
			for(Member member : memberList) {
				String memberLevel = member.getMemberLevel();
				switch (memberLevel) {
				case "1":
					member.setMemberLevelName("관리자");
					break;
				case "2":
					member.setMemberLevelName("판매자");
					break;
				default:
					member.setMemberLevelName("구매자");
					break;
				}
			}
			*/
			memberList.forEach(member -> {
				String memberLevel = member.getMemberLevel();
				switch (memberLevel) {
				case "1":
					member.setMemberLevelName("관리자");
					break;
				case "2":
					member.setMemberLevelName("판매자");
					break;
				default:
					member.setMemberLevelName("구매자");
					break;
				}
			});
		}

		return memberList;
	}
	
	
	
	
	
	
	
	
	
}
