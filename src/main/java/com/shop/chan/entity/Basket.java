package com.shop.chan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="basket")
public class Basket {
    @Id
    @Column(name="basket_id")
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name="basket_id")
    private Member member;

    @OneToMany(mappedBy = "basket")
    private List<BasketItem> basketItemList = new ArrayList<>();

    //카트에 담긴 총 상품 개수
    private int count;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate; // 날짜

    @PrePersist
    public void createDate(){
        this.createDate = LocalDate.now();
    }

    //장바구니 생성
    public static Basket createBasket(Member member){
        Basket basket = new Basket();
        basket.setCount(0);
        basket.setMember(member);
        return basket;
    }
}
