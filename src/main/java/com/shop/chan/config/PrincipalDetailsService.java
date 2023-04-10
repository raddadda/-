package com.shop.chan.config;

import com.shop.chan.entity.Member;
import com.shop.chan.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // UserDetailsService 는 DB에서 회원 정보를 가져오는 인터페이스
    // -> loadUserByUsername() 메소드를 통해 회원정보 조회 -> UserDetails 인터페이스 반환
    // UserDetails 는 회원 정보를 담는 인터페이스
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Member member = memberRepository.findByEmail(username);

        Member memberEntity = memberRepository.findByName(username);

        if (memberEntity == null) {
            return null;
        } else {
            return new PrincipalDetails(memberEntity);
        }
    }
}