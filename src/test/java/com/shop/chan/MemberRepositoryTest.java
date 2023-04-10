/*
package com.shop.chan;


import com.shop.chan.dto.MemberFormDto;
import com.shop.chan.entity.Member;
import com.shop.chan.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

//오류 해결
@SpringBootTest
@Transactional
 class MemberRepositoryTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();

        memberFormDto.setEmail("123");
        memberFormDto.setAddress("1234");
        memberFormDto.setPassword("12345");
        memberFormDto.setAddress("1-11");
        return Member.createMember(memberFormDto,passwordEncoder);

    }
    @Test
    public void 회원가입_테스트() throws Exception{
        Member member = new Member();

        Member savedMember = memberService.join(member);

        //assertEquals(member, memberRepository.findOne(savedId));

        assertEquals(member.getName(),savedMember.getName());
        assertEquals(member.getAddress(),savedMember.getAddress());
        assertEquals(member.getEmail(),savedMember.getEmail());
        assertEquals(member.getPassword(),savedMember.getPassword());
        assertEquals(member.getRole(),savedMember.getRole());
        System.out.println("@@@@"+savedMember);

    }
 }
*/
