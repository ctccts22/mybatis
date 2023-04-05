package ksmart.mybatis.dto;

import lombok.Data;

import java.util.List;

@Data
public class Member {
	private String memberId;
	private String memberPw;
	private String memberLevel;
	private String memberLevelName;
	private String memberName;
	private String memberAddr;
	private String memberEmail;
	private String memberRegDate;
	private List<Goods> sellerList;
}
