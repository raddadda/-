package com.shop.chan.service;

import com.shop.chan.dto.OrderDto;


import com.shop.chan.entity.*;
import com.shop.chan.repository.ItemRepository;
import com.shop.chan.repository.MemberRepository;
import com.shop.chan.repository.OrderItemRepository;
import com.shop.chan.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {


   // private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MemberService memberService;

  //  private final ItemRepository itemRepository;
    private final ItemService itemService;


    //주문 생성
    public void createOrder(Member member){
        Order order = Order.createOrder(member);

        orderRepository.save(order);
    }

    //회원id로 해당하는 주문아이템리스트 조회
    //회원의
    public List<OrderItem> findMemberOrderItems(Long memberid){
        return orderItemRepository.findOrderItemsByMemberId(memberid);
    }

    //주문아이템 id로 OrderItem 하나 조회
    public OrderItem findOrderitem(Long orderItemId){
        return orderItemRepository.findOrderItemsById(orderItemId);
    }

    //장바구니 상품주문
    @Transactional
    public OrderItem addBasketOrder(Long itemId, Long memberId, BasketItem basketItem){
        //멤버서비스의 멤버, 즉 해당 로그인된 회원의 아이디 가져오기
        Member member = memberService.findMember(memberId);
        //오더아이템의 오더아이템생성, 해당 아이템 아이디,회원, 장바구니 아이템
        OrderItem orderItem = OrderItem.createOrderItem(itemId, member, basketItem);

        orderItemRepository.save(orderItem);

        return orderItem;
    }

    //주문하면 오더 만들기
    @Transactional
    public void addOrder(Member member, List<OrderItem> orderItemList){
        //Order에서 주문 생성
        Order memberOrder = Order.createOrder(member, orderItemList);

        orderRepository.save(memberOrder);
    }

    //개별 상품 주문
/*    @Transactional
    public void addOneOrder(Long memberId, Item item, int count){
        Member member = memberService.findMember(memberId);
        Order memberOrder = Order.createOrder(member);
        OrderItem orderItem = OrderItem.createOrderItem(item.getId(),member,item,count,memberOrder);
    }*/

    //주문 취소 기능
    @Transactional
    public void orderCancel(Member member, OrderItem cancelItem){
        //itemservice에서 아이템 취소할 아이템id를 통해 아이템id 조회
        Item item = itemService.itemDetail(cancelItem.getItemId());

        //판매자의 판매내역 totalCount 감소
        //아이템의 재고 = 아이템의 재고 + 취소할 아이템 수량
        item.setStockQuantity(item.getStockQuantity()+cancelItem.getItemCount());

        //해당 item의 판매량 감소
        item.setCount(item.getCount()-cancelItem.getItemCount());

        //해당 orderItem의 주문 상태를 주문 취소로 변경
        cancelItem.setIsCancel(cancelItem.getIsCancel()+1);

        orderItemRepository.save(cancelItem);
    }

    /*   public Long order(OrderDto orderDto,String email){
        //주문상품 객체 생성
        List<OrderItem> orderItemList = new ArrayList<>();
        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        orderItemList.add(OrderItem.createOrderItem(item, orderDto.getCount()));

        //Order 객체 생성
        Member member = memberRepository.findByEmail(email);
        Order order = Order.createOrder(member,orderItemList);

        orderRepository.save(order);
        return order.getId();
    }*/
/*//주문 내역 조회
    @Transactional(readOnly = true)
    public List<OrderListDto> getOrderList(String email) {
        List<Order> orders = orderRepository.findOrders(email);
        Long totalCount = orderRepository.countOrder(email);

        List<OrderListDto> orderListDtos = new ArrayList<>();

        for (Order order : orders) {
            OrderListDto orderListDto = new OrderListDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                OrderItemDto orderItemDto = new OrderItemDto(orderItem);
                orderListDto.addOrderItemDto(orderItemDto);
            }
            orderListDtos.add(orderListDto);
        }
      return orderListDtos;
    }*/

  /*  //주문한 유저 검증

    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String email){
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        if(StringUtils.equals(order.getMember().getEmail(),email)){
            return true;
        }
        return false;
    }

    //주문 취소

    public void orderCancel(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        order.orderCancel();
    }

    //장바구니 담긴 상품 주문

    public Long orders(List<OrderDto> orderDtoList, String email){
        //로그인 유저 조회
        Member member = memberRepository.findByEmail(email);
        //orderDto에서 아이템과 count값을 가져온다.
        List<OrderItem> orderItemList = new ArrayList<>();
        for(OrderDto orderDto : orderDtoList){
            Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
            OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
            orderItemList.add(orderItem);
        }

        //Order 엔티티에서 createOrder를 통해 order를 만든다.
        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);
        return order.getId();
    }*/
}

