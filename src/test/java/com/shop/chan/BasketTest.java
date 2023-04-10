/*
package com.shop.chan;


import com.shop.chan.dto.MemberFormDto;
import com.shop.chan.entity.Basket;
import com.shop.chan.entity.Member;
import com.shop.chan.repository.BasketRepository;
import com.shop.chan.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class BasketTest {
    @Autowired
    BasketRepository basketRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EntityManager em;


    public Member createMembers(){
        MemberFormDto memberFormDto = new MemberFormDto();

        memberFormDto.setEmail("11@11");
        memberFormDto.setName("안젤라");
        memberFormDto.setAddress("청파동");
        memberFormDto.setPassword("486");

        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    public void findBasketAndMemberTest(){
        Member member = createMembers();
        memberRepository.save(member);

        Basket basket = new Basket();
        basket.setMember(member);
        basketRepository.save(basket);

        em.flush();
        em.clear();

        Basket savedBasket = basketRepository.findById(basket.getId()).orElseThrow(EntityNotFoundException::new);
        assertEquals(savedBasket.getMember().getId(),member.getId());

    }
}
*/
