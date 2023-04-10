package com.shop.chan.repository;

import com.shop.chan.entity.Basket;
import com.shop.chan.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Member findByEmail(String email);

    Member findByName(String name);
    //기본 문법과 겹치는것 오류 해결


}
