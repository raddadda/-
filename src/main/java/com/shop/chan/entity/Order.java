package com.shop.chan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;   //order가 외래키

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate; // 구매 날짜

    @PrePersist
    public void createDate() {
        this.createDate = LocalDate.now();
    }

    //orderitem이 외래키
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    //주문 상품 추가
    public void addOrderItem(OrderItem orderItem){
        //주문상품을 orderItem 리스트에 넣는다.
        orderItems.add(orderItem);
        //orderItem에 order 변경 내용을 똑같이 맞춰주는것
        orderItem.setOrder(this);
    }
    //주문 생성
    public static Order createOrder(Member member, List<OrderItem> orderItemList){
        Order order = new Order();
        //주문에서 해당 회원을 가져온다.
        order.setMember(member);
        //주문 상품 리스트에서 상품을 하나씩 가져와서 주문상품추가에 넣는다.
        for(OrderItem orderItem : orderItemList){
            order.addOrderItem(orderItem);
        }
        order.setCreateDate(order.createDate);
        return order;
    }
    //개별 주문 생성
    public static Order createOrder(Member member){
        Order order = new Order();
        order.setMember(member);
        order.setCreateDate(order.createDate);
        return order;
    }

}
