package com.shop.chan;

import com.shop.chan.dto.MemberFormDto;
import com.shop.chan.entity.Member;
import com.shop.chan.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
//@TestPropertySources(locations= "c")
 class MemberControllerTest {
        @Autowired
    private MemberService memberService;

        @Autowired
    private MockMvc mockMvc;

        @Autowired
    PasswordEncoder passwordEncoder;

        //회원 등록

/*    public Member createMember(String email, String password ){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setName("가나다");
        memberFormDto.setEmail(email);
        memberFormDto.setAddress("후암동");
        memberFormDto.setPassword(password);

        Member member = Member.createMember(memberFormDto, passwordEncoder);
        return memberService.join(member);
    }*/

    @Test
    public void loginTest() throws Exception{
        String email = "123@123.com";
        String password = "1234";
      //  this.createMember(email,password);
        mockMvc.perform(formLogin()
                .userParameter("email")
                .loginProcessingUrl("/members/login")
                .user(email)
                .password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());

                System.out.println("로그인 성공 테스트 :: "+ email);
                System.out.println("로그인 성공 테스트 :: "+ password);

    }

}


