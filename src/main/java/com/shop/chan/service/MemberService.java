package com.shop.chan.service;

import com.shop.chan.entity.Member;
import com.shop.chan.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService
        //implements UserDetailsService
{
    private final MemberRepository memberRepository;

   /* private final BasketService basketService;
    private final OrderService orderService;*/
   // private final BCryptPasswordEncoder bCryptPasswordEncoder;



    /*//회원 가입
    @Transactional
    public Member join(Member member){
        //동일한 멤버가 있는지 검사를 한다.
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }


    private void validateDuplicateMember(Member member){
        Member findMember = memberRepository.findByEmail(member.getEmail());

        if(findMember != null) {

            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }*/

    //id로 회원 조회
    public Member findMember(Long id){
        return memberRepository.findById(id).get();
    }
/*
    public Member findOne(Long id){
        return memberRepository.findOne(id);
    }
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }*/

/*    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if(member == null){
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }*/
}
