package com.shop.chan.controller;

import com.shop.chan.dto.MemberFormDto;
import com.shop.chan.entity.Member;
import com.shop.chan.service.JoinService;
import com.shop.chan.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class JoinController {

    private final JoinService joinService;

    //로그인화면으로 가기

    //URL은 signin
    @GetMapping("/signin")
    public String SigninForm() {
        //view의 signin으로 이동한다.
        return "signin";
    }

    //회원가입화면으로 가기
    @GetMapping("/signup")
    public String SignupForm() {
        return "signup";
    }

    //PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    //
    @PostMapping("/signup")
    public String memberForm(MemberFormDto memberFormDto) {
        //회원가입에서 사용자가 입력한 정보가 있는 Dto
        Member member = memberFormDto.toEntity();

        //Dto를 joinservice에 넘겨서 가입을 하도록 한다.
        Member memberEntity = joinService.join(member);

        //제출을 누르면 로그인화면으로 이동하도록 한다.
        return "signin";
    }
    /*@GetMapping("/logout")
    public String logout(HttpSession session) throws Exception{
        joinService.logout(session);
        return "redirect:/signin";
    }*/
}
