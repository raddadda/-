package com.shop.chan.repository;

import com.shop.chan.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
Basket findByMemberId(Long memberId);

Basket findBasketByMemberId(Long id);
}
