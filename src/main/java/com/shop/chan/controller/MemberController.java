package com.shop.chan.controller;

import com.shop.chan.entity.Member;
import com.shop.chan.dto.MemberFormDto;
import com.shop.chan.service.JoinService;
import com.shop.chan.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController{

   /* private final MemberService memberService;

    @GetMapping("/signin")
    public String SigninForm() {
        return "signin";
    }

    @GetMapping("/signup")
    public String SignupForm() {
        return "signup";
    }
    //PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    @PostMapping("/signup")
    public String memberForm(MemberFormDto memberFormDto) {
        Member member = memberFormDto.toEntity();

        Member memberEntity = JoinService.join(member);
        System.out.println(memberEntity);
        return "signin";
    }*/
/*    @PostMapping("/signup")
    public String memberForm(MemberFormDto memberFormDto, Model model){
        model.addAttribute("memberFormDto",memberFormDto);
        log.info("회원가입페이지로 이동 성공");
        return "members/memberForm";
    }*/
    //


  /*  @PostMapping("/members/new")
    public String memberForm(MemberFormDto memberFormDto) {
        Member member = memberFormDto.toEntity();

        Member memberEntity = memberService.join(member);
        System.out.println(memberEntity);
        return "redirect:/";*/

       /* if(bindingResult.hasErrors()) {
            return "members/memberForm";
        }

        try {
            Member member = Member.createMember(memberFormDto,passwordEncoder);
            memberService.join(member);
            log.info("회원가입join완료");
            System.out.println("@@@@@ 입력한거 저장되는지확인하기" + member);
        }catch(IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "members/memberForm";
        }
        log.info("가입후 홈페이지 돌아가기");
        return "redirect:/";*/
    //}

/*    @GetMapping("/members/login")
    public String loginMember(){
        log.info("로그인페이지이동성공");
        return "members/memberLoginForm";
    }

    @GetMapping("/members/login/error")
    public String loginError(Model model){
        model.addAttribute("loginError","아이디 또는 비밀번호가 틀렸습니다.");
        log.info("가입 오류");
        return "members/memberLoginForm";
    }*/

   /* @GetMapping("/members/logout")
    public String logoutMember(){
        log.info("로그아웃후 홈으로");
        return "redirect:/";
    }*/
}
