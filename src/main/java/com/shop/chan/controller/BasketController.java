package com.shop.chan.controller;

import com.shop.chan.config.PrincipalDetails;
import com.shop.chan.dto.BasketItemDto;
import com.shop.chan.dto.BasketListDto;
import com.shop.chan.dto.BasketOrderDto;
import com.shop.chan.entity.*;
import com.shop.chan.service.BasketService;
import com.shop.chan.service.ItemService;
import com.shop.chan.service.MemberService;
import com.shop.chan.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Past;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
@Controller
@Slf4j
@RequiredArgsConstructor
public class BasketController {
    private final MemberService memberService;
    private final BasketService basketService;
    private final ItemService itemService;
    private final OrderService orderService;


    //장바구니 페이지 접속
    @GetMapping("/basket/{id}")
    public String memberBasketPage(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        //로그인 id와 장바구니 접속 id 체크
        if (principalDetails.getMember().getId() == id) {
            Member member = memberService.findMember(id);
            //로그인 유저의 장바구니 가져오기
            Basket memberBasket = member.getBasket();
            //장바구니 아이템리스트 모두 가져오기
            List<BasketItem> basketItemList = basketService.allMemberBasketDetail(memberBasket);

            //장바구니에 들어있는 상품의 총 가격
            int totalPrice = 0;
            for (BasketItem basketitem : basketItemList) {
                totalPrice += basketitem.getCount() * basketitem.getItem().getPrice();
            }
            //장바구니 전체 가격
            model.addAttribute("totalPrice", totalPrice);
            //총 장바구니 상품 수량
            model.addAttribute("totalCount", memberBasket.getCount());
            //장바구니 아이템 리스트
            model.addAttribute("basketItems", basketItemList);
            //해당장바구니 주인
            model.addAttribute("member", memberService.findMember(id));
            return "basket/basketList";
        } else {
            System.out.println("로그인 id와 장바구니 id가 다릅니다.");
            return "redirect:/main";
        }
    }

    // 장바구니 아이템 담기
    @PostMapping("/basket/{id}/{itemId}")
    public String addBasketItem(@PathVariable("id") Long id, @PathVariable("itemId") Long itemId, int amount) {
        //회원 조회
        Member member = memberService.findMember(id);
        //아이템 서비스에서 해당 아이템아이디로 상품 하나씩 가져오기
        Item item = itemService.itemDetail(itemId);
        //장바구니 서비스의 addBasket메서드
        basketService.addBasket(member, item, amount);

        return "redirect:/item/detail/{itemId}";
    }

    //장바구니 아이템 빼기
    @GetMapping("/basket/{id}/{basketItemId}/delete")
    public String deleteBasketItem(@PathVariable("id") Long id, @PathVariable("basketItemId")Long itemId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        if(principalDetails.getMember().getId()==id){
            //itemId로 해당 장바구니속 아이템 찾기
            BasketItem basketItem = basketService.findBasketItemById(itemId);
            //해당 회원의 장바구니 조회
            Basket memberBasket = basketService.findMemberBasket(id);
            //장바구니 전체 수량 감소(회원 장바구니 수량-해당 아이템 수량)
            memberBasket.setCount(memberBasket.getCount()-basketItem.getCount());
            //장바구니 서비스에서 해당 아이템 아이디를 통해 아이템삭제
            basketService.basketItemDelete(itemId);
            //해당 id 회원의 장바구니 상품 조회
            List<BasketItem> basketItemList = basketService.allMemberBasketDetail(memberBasket);

            //총 가격 += 수량 * 가격
            int totalPrice = 0;
            for(BasketItem basketItem1 : basketItemList){
                totalPrice += basketItem1.getCount() * basketItem.getItem().getPrice();
            }
            //장바구니 전체 가격
            model.addAttribute("totalPrice", totalPrice);
            //장바구니 수량
            model.addAttribute("totalCount",memberBasket.getCount());
            //장바구니 리스트
            model.addAttribute("basketItems",basketItemList);
            //해당장바구니 회원
            model.addAttribute("member", memberService.findMember(id));
            return "basket/basketList";
        }else{
            return "redirect:/main";
        }

    }
    //주문 내역 조회
    @GetMapping("/orderHist/{id}")
    public String orderList(@PathVariable("id") Long id , @AuthenticationPrincipal PrincipalDetails principalDetails,Model model){
        if(principalDetails.getMember().getId() == id){
            //로그인된 해당 회원의 구매내역 가져오기
            List<OrderItem> orderItemList = orderService.findMemberOrderItems(id);

            //총 주문 개수
            int totalCount = 0;

            for(OrderItem orderItem : orderItemList){
                //오더아이템이 주문취소가 아닐 경우 수량 증가
                if(orderItem.getIsCancel() != 1){
                    totalCount += orderItem.getItemCount();
                }
            }
            //총 수량
            model.addAttribute("totalCount", totalCount);
            //주문 아이템 리스트
            model.addAttribute("orderItems", orderItemList);
            //회원
            model.addAttribute("member", memberService.findMember(id));

            return "members/memberOrderList";
        } else{
            return "redirect:/main";
        }

    }
    //장바구니 상품 전체 주문
    @Transactional
    @PostMapping("/basket/checkout/{id}")
    public String basketCheckout(@PathVariable("id") Long id, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        //로그인 회원과 주문하는 회원의 아이디 동일인인지 체크
        if (principalDetails.getMember().getId() == id) {
            Member member = memberService.findMember(id);

            // 회원의 장바구니 찾기
            Basket memberBasket = basketService.findMemberBasket(member.getId());

            // 장바구니 리스트 조회
            List<BasketItem> memberBasketItems = basketService.allMemberBasketDetail(memberBasket);
            //최종 결제 금액
            int totalPrice = 0;
            for (BasketItem basketItem : memberBasketItems) {
                //재고가 없거나, 주문 수량이 남은 재고보다 클경우
                if (basketItem.getItem().getStockQuantity() == 0 || basketItem.getItem().getStockQuantity() < basketItem.getCount()) {
                    return "redirect:/main";
                }
                totalPrice += basketItem.getCount() * basketItem.getItem().getPrice();
            }
            //주문 리스트 조회
            List<OrderItem> orderItemList = new ArrayList<>();
            //장바구니 아이템 하나씩 조회
            for (BasketItem basketItem : memberBasketItems) {
                //재고 수량 감소
                basketItem.getItem().setStockQuantity(basketItem.getItem().getStockQuantity() - basketItem.getCount());
                //상품 개별로 판매 개수 증가
                basketItem.getItem().setCount(basketItem.getItem().getCount() + basketItem.getCount());
                //장바구니 상품 주문
                OrderItem orderItem = orderService.addBasketOrder(basketItem.getItem().getId(), member.getId(), basketItem);
                //주문아이템리스트 추가
                orderItemList.add(orderItem);
            }
            //주문 추가하기
            orderService.addOrder(member, orderItemList);
            //장바구니 상품 모두 삭제
            basketService.allBasketItemDelete(id);

            //총 수량
            model.addAttribute("totalPrice", totalPrice);
            //장바구니 아이템 리스트
            model.addAttribute("basketItems", memberBasketItems);
            //회원
            model.addAttribute("member", memberService.findMember(id));

            return "redirect:/basket/{id}";
        } else {
            return "redirect:/main";
        }

    }

    //개별 주문
 /*   @Transactional
    @PostMapping("/{id}/checkout/{itemId}")
    public String checkout(@PathVariable("id") Long id, @PathVariable("itemId") Long itemId, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model, int count) {
        if (principalDetails.getMember().getId() == id) {
            Member member = memberService.findMember(id);
            Item item = itemService.itemDetail(itemId);

            //상품 재고가 0이거나 재고가 적을 경우

            if (item.getStockQuantity() == 0 || item.getStockQuantity() < count) {
                return "redirect:/main";
            }

            // 최종 결제 금액
            int totalPrice = item.getPrice() * count;
            // 구매한만큼 재고 감소, 구매한만큼 수량 증가
            item.setStockQuantity(item.getStockQuantity() - count);
            item.setCount(item.getCount() + count);
            orderService.addOneOrder(member.getId(), item, count);


            return "redirect:/orderHist/{id}";
        } else {
            return "redirect:/main";
        }
    }*/

    //주문 취소 기능
    @PostMapping("/{id}/checkout/cancel/{orderItemId}")
    public String cancelOrder(@PathVariable("id") Long id, @PathVariable("orderItemId") Long orderItemId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
    if(principalDetails.getMember().getId()==id){
        //취소할 상품 조회
        OrderItem cancelItem = orderService.findOrderitem(orderItemId);
        //취소할 유저 찾기
        Member member = memberService.findMember(id);

        //주문 내역 총 개수-취소 상품 개수
        List<OrderItem> orderItemList = orderService.findMemberOrderItems(id);
        int totalCount = 0;
        for(OrderItem orderItem : orderItemList){
            totalCount += orderItem.getItemCount();
        }
        //전체 카운트-취소할 상품 카운트
        totalCount = totalCount - cancelItem.getItemCount();

        //orderService의 주문취소 메서드
        orderService.orderCancel(member,cancelItem);

        //총 수량
        model.addAttribute("totalCount", totalCount);
        //주문 아이템 리스트
        model.addAttribute("orderItems", orderItemList);
        //회원
        model.addAttribute("member", member);

        return "redirect:/orderHist/{id}";
    }else{
        return "redirectL:/main";
    }


    }
}

