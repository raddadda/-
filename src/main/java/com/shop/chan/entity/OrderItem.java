package com.shop.chan.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name="order_item_id")
    private Long id; // 기본키
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order; // 외래키

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // 외래키


   private Long itemId; // 주문 상품 번호
   private String itemName; // 주문 상품 이름
   private int itemPrice; // 주문 상품 가격
   private int itemCount; // 주문 상품 수량
   private int itemTotalPrice; // 가격*수량

    private int isCancel; //0:주문완료 1:주문 취소

    // 장바구니 전체 조회
public static OrderItem createOrderItem(Long itemId,Member member,BasketItem basketItem){
    OrderItem orderItem = new OrderItem();
    //오더아이템에 해당 아이템 아이디 저장
    orderItem.setItemId(itemId);
    //회원 저장
    orderItem.setMember(member);
    //장바구니의 아이템의 이름 조회 후 저장
    orderItem.setItemName(basketItem.getItem().getItemName());
    //장바구니의 아이템의 가격 조회 후 저장
    orderItem.setItemPrice(basketItem.getItem().getPrice());
    //장바구니의 아이템의 수량 조회 후 저장
    orderItem.setItemCount(basketItem.getCount());
    //장바구니의 아이템의 가격 * 장바구니의 수량 곱 저장(총 가격)
    orderItem.setItemTotalPrice(basketItem.getItem().getPrice()*basketItem.getCount());

    return orderItem;
}

//개별 주문
/*public static OrderItem createOrderItem(Long itemId, Member member, Item item, int count, Order order){
    OrderItem orderItem = new OrderItem();
    orderItem.setItemId(itemId);
    orderItem.setMember(member);
    orderItem.setOrder(order);
    orderItem.setItemName(item.getItemName());
    orderItem.setItemPrice(item.getPrice());
    orderItem.setItemCount(count);
    orderItem.setItemTotalPrice(item.getPrice()*count);
    return orderItem;
}*/
   /* private int orderPrice;
    private int count;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;*/

  /*  public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice());

        item.removeStock(count);
        return orderItem;

    }*/

   /* public void cancel(){
        this.getItem().addStock(count);
    }*/
}
