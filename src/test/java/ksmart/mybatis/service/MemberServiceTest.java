package ksmart.mybatis.service;

import ksmart.mybatis.dto.Member;
import ksmart.mybatis.mapper.MemberMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberMapper memberMapper;

    @InjectMocks
    private MemberService memberService;

    private List<Member> memberList;

    @BeforeEach
    public void setUp() {
        Member member1 = new Member();
        member1.setMemberId("1");
        member1.setMemberName("user1");
        member1.setMemberEmail("email1@example.com");

        Member member2 = new Member();
        member2.setMemberId("2");
        member2.setMemberName("user2");
        member2.setMemberEmail("email2@example.com");

        memberList = Arrays.asList(member1, member2);
    }

    @Test
    public void getMemberListTest() {
        // Given
        when(memberMapper.getMemberList()).thenReturn(memberList);

        // When
        List<Member> result = memberService.getMemberList();

        // Then
        assertEquals(memberList, result);
    }
}
