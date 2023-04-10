package com.shop.chan.controller;

import com.shop.chan.config.PrincipalDetails;
/*import com.shop.chan.dto.ItemFormDto;*/
import com.shop.chan.entity.Basket;
import com.shop.chan.entity.BasketItem;
import com.shop.chan.entity.Item;
import com.shop.chan.entity.Member;
import com.shop.chan.repository.ItemRepository;
import com.shop.chan.service.BasketService;
import com.shop.chan.service.ItemService;
import com.shop.chan.service.MemberService;
import com.shop.chan.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;

    private final ItemService itemService;

    private final MemberService memberService;

    private final BasketService basketService;

    // 메인화면(비회원 유저)
    @GetMapping("/")
    public String mainPageNoneLogin(Model model) {
        //상품리스트 불러오기
        List<Item> items = itemService.allItemView();

        //상품리스트
        model.addAttribute("items", items);

        //메인 화면
        return "main";
    }

    // 메인화면(회원 유저) admin,일반유저 둘다
    @GetMapping("/main")
    public String mainPage(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        //구매자 id
        Long userId = principalDetails.getMember().getId();
        //상품리스트 불러오기
        List<Item> items = itemService.allItemView();

        //상품리스트
        model.addAttribute("items", items);
        //회원정보
        model.addAttribute("member", memberService.findMember(userId));

        //메인화면
        return "/main";
    }

    //상품 등록페이지
    @GetMapping("/admin/item/new")
    public String itemSaveForm(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        model.addAttribute("member", principalDetails.getMember());

        return "admin/itemForm";
    }

    //상품 등록페이지 누르면
    @PostMapping("/admin/item/new/pro")
    public String itemSave(Item item,@AuthenticationPrincipal PrincipalDetails principalDetails,MultipartFile imgFile) throws Exception {

            itemService.saveItem(item, imgFile);
            return "redirect:/main";

    }

    //로그인 했을때 상품 리스트
    @GetMapping("/item/list")
    public String itemList(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                           String searchKeyword, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = memberService.findMember(principalDetails.getMember().getId());

        Page<Item> items = null;
        //검색이 들어오지 않았을 때
        if (searchKeyword == null) {

            items = itemService.allItemViewPage(pageable);
        } else {
            items = itemService.itemSearchList(searchKeyword, pageable);
        }

        int nowPage = items.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, items.getTotalPages());

        model.addAttribute("items", items);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("member", member);

        return "itemList";
    }

    //상품 리스트 페이지 - 로그인 안했을때
    @GetMapping("/nonlogin/item/list")
    public String itemList(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                           String searchKeyword) {

        Page<Item> items = null;

        if (searchKeyword == null) {
            items = itemService.allItemViewPage(pageable);
        } else {
            items = itemService.itemSearchList(searchKeyword, pageable);
        }

        int nowPage = items.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, items.getTotalPages());

        model.addAttribute("items", items);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        return "itemList";
    }

    //상품 상세 페이지 (로그인 안했을때)
    @GetMapping("/item/detail/nonlogin/{id}")
    public String nonLoginItemDetail(Model model, @PathVariable("id") Long id) {
        model.addAttribute("item", itemService.itemDetail(id));
        return "items/itemDetail";
    }

    //상품 상세 페이지 (로그인 했을때)
    @GetMapping("/item/detail/{itemId}")
    public String ItemDetail(Model model, @PathVariable("itemId") Long id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        //어드민일때
        if (principalDetails.getMember().getRole().equals("ROLE_ADMIN")) {

            Member member = principalDetails.getMember();
            //상품 하나 조회
            model.addAttribute("item", itemService.itemDetail(id));
            //회원
            model.addAttribute("member", member);

            return "items/itemDetail";
        }
        //일반회원일때
        else {
            Member member = principalDetails.getMember();
            //해당id찾기
            Member loginMember = memberService.findMember(member.getId());
            //해당회원의 id의 장바구니찾기
            int basketCount = 0;
            Basket memberBasket = basketService.findMemberBasket(loginMember.getId());
            List<BasketItem> basketItems = basketService.allMemberBasketDetail(memberBasket);

            for (BasketItem basketItem : basketItems) {
                basketCount += basketItem.getCount();
            }
            //장바구니 카운트
            model.addAttribute("basketCount", basketCount);
            //상품 하나 불러오기
            model.addAttribute("item", itemService.itemDetail(id));
            //회원
            model.addAttribute("member", member);

            return "items/itemDetail";
        }
    }

    //상품 수정 페이지
    @GetMapping("/item/modify/{id}")
    public String itemModify(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        //어드민전용
        if(principalDetails.getMember().getRole().equals("ROLE_ADMIN")){
            //아이템 상세페이지
           model.addAttribute("item", itemService.itemDetail(id));
           //회원
           model.addAttribute("member",principalDetails.getMember());

           return "/items/itemModify";
        } else {
            return "redirect:/main";
        }
    }

    //상품 수정 버튼
    @PostMapping("/item/modify/pro/{id}")
    public String itemModify(Item item,@PathVariable("id") Long id, @AuthenticationPrincipal PrincipalDetails principalDetails, MultipartFile imgFile) throws Exception{
        //어드민 전용
        if(principalDetails.getMember().getRole().equals("ROLE_ADMIN")){

            //상품 수정 메소드
            itemService.itemModify(item, id,imgFile);

            return "redirect:/main";


        }else{
            return "redirect:/main";
        }
    }


}


