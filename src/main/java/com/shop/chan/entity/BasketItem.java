package com.shop.chan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="basket_item")
public class BasketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="basket_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="basket_id")
    private Basket basket;      //장바구니

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;          //아이템

    //동일 상품 담은 수량
    private int count;     //담은 수량

    //장바구니아이템 생성
    public static BasketItem createBasketItem(Basket basket, Item item,int amount){
        BasketItem basketItem =  new BasketItem();
        basketItem.setBasket(basket);
        basketItem.setItem(item);
        basketItem.setCount(amount);
        return basketItem;
    }
    //이미 담겨져있는 물건에 추가할 경우 수량 증가
    public void addCount(int count){
        this.count += count;
    }
}
