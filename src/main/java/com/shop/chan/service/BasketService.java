package com.shop.chan.service;

import com.shop.chan.dto.BasketItemDto;
import com.shop.chan.dto.BasketListDto;
import com.shop.chan.dto.BasketOrderDto;
import com.shop.chan.dto.OrderDto;
import com.shop.chan.entity.Basket;
import com.shop.chan.entity.BasketItem;
import com.shop.chan.entity.Item;
import com.shop.chan.entity.Member;
import com.shop.chan.repository.BasketItemRepository;
import com.shop.chan.repository.BasketRepository;
import com.shop.chan.repository.ItemRepository;
import com.shop.chan.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
//@Transactional
@RequiredArgsConstructor
public class BasketService {

    //장바구니 레포지토리
    private final BasketRepository basketRepository;
    //아이템 레포지토리
    private final ItemRepository itemRepository;
    //장바구니아이템 레포지토리
    private final BasketItemRepository basketItemRepository;

    //회원가입 후 카트 생성, joinservice에서 회원 등록시 바로 사용됨
    public void createBasket(Member member){

        Basket basket = Basket.createBasket(member);

        basketRepository.save(basket);

    }

    //장바구니 담기

    @Transactional
    public void addBasket(Member member, Item newItem, int amount){
        // 유저 id의 해당 장바구니 조회
        Basket basket = basketRepository.findByMemberId(member.getId());
        //장바구니 존재하지 않으면 새로 만든다.
        if(basket == null){
            basket = Basket.createBasket(member);
            basketRepository.save(basket);
        }

        //새로 추가된 아이템의 id 조회
        Item item = itemRepository.findItemById(newItem.getId());
        //장바구니의 아이디와 아이템 id 조회
        BasketItem basketItem = basketItemRepository.findByBasketIdAndItemId(basket.getId(), item.getId());


        //상품이 장바구니에 존재하지 않으면 새로 장바구니아이템을 생성 후 추가한다
        if(basketItem == null){
            basketItem = BasketItem.createBasketItem(basket,item,amount);
            basketItemRepository.save(basketItem);
        }
        //상품이 장바구니에 이미 존재한다면 수량만 증가시킨다
        else{
            //위의 장바구니아이템을 조회한것으로 업데이트 시키는 과정
            BasketItem update = basketItem;
            update.setBasket(basketItem.getBasket());
            update.setItem(basketItem.getItem());
            update.addCount(amount);
            update.setCount(update.getCount());
            basketItemRepository.save(update);
        }

        //장바구니 상품 총 개수 증가
        basket.setCount(basket.getCount()+amount);
    }


    // 회원id로 해당 회원의 장바구니 찾기
    public Basket findMemberBasket(Long memberId){
        return basketRepository.findBasketByMemberId(memberId);
    }

    //장바구니 상품 리스트중에서 해당 회원이 담은 상품만 반환
    //회원의 장바구니 id와 장바구니상품의 장바구니 id가 같아야함
    public List<BasketItem> allMemberBasketDetail(Basket memberBasket){
        // 회원의 바구니 id를 꺼냄
        Long memberBasketId = memberBasket.getId();

        //id에 해당하는 회원이 담은 상품들 넣어둘 곳
        List<BasketItem> MemberBasketItems = new ArrayList<>();
        //장바구니에 있는 모든 상품 불러오기
        List<BasketItem> BasketItems = basketItemRepository.findAll();

        for(BasketItem basketItem : BasketItems){
            //해당 회원의 장바구니를 찾아서 장바구니 아이템을 반환시킨다.
            if(basketItem.getBasket().getId() == memberBasketId){
                MemberBasketItems.add(basketItem);
            }
        }

        return MemberBasketItems;
    }

    // 장바구니 상품 리스트중에서 해당하는 상품 id의 상품만 반환하기
    public List<BasketItem> findBasketItemByItemId(Long id){
        List<BasketItem> basketItems = basketItemRepository.findBasketItemByItemId(id);

       return basketItems;
    }
    // 장바구니 상품 리스트 중 해당하는 상품 id만 반환
    public BasketItem findBasketItemById(Long id){
        BasketItem basketItem = basketItemRepository.findBasketItemById(id);

        return basketItem;
    }
    //장바구니의 상품 개별 삭제
    public void basketItemDelete(Long id){
        basketItemRepository.deleteById(id);
    }
    //장바구니 아이템 전체 삭제
    public void allBasketItemDelete(Long id){
        //전체 basketItem 찾기
        List<BasketItem> basketItems = basketItemRepository.findAll();
        //반복문을 이용하여 해당하는 멤버의 BasketItem만 찾아서 삭제한다.
        for(BasketItem basketItem : basketItems){
            if(basketItem.getBasket().getMember().getId()==id){

                basketItem.getBasket().setCount(0);

                basketItemRepository.deleteById(basketItem.getId());
            }
        }
    }

}
