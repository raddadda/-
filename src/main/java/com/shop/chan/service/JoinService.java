package com.shop.chan.service;

import com.shop.chan.entity.Member;
import com.shop.chan.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class JoinService {

    private final MemberRepository memberRepository;
    private final BasketService basketService;
    private final OrderService orderService;

    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    @Transactional
    public Member join(Member member){
        //사용자 비밀번호
        String realPassword = member.getPassword();
        //사용자 비밀번호를 시큐리티를 통해 암호화
        String encPassword = passwordEncoder.encode(realPassword);

        member.setPassword(encPassword);

        member.setRole(member.getRole());

        Member memberEntity = memberRepository.save(member);
        //회원만들면 장바구니 서비스에서 기본 장바구니를 만든다.
        basketService.createBasket(member);
        //회원만들면 기본 주문 하나 생성
        orderService.createOrder(member);

        return memberEntity;
    }
}
