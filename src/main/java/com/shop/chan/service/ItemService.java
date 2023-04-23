package com.shop.chan.service;

/*import com.shop.chan.dto.ItemFormDto;*/
import com.shop.chan.config.PrincipalDetails;
import com.shop.chan.dto.ItemSearchDto;
import com.shop.chan.dto.MainItemDto;
import com.shop.chan.entity.BasketItem;
import com.shop.chan.entity.Item;
import com.shop.chan.entity.Member;
import com.shop.chan.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final BasketService basketService;
    @Transactional
    //상품 등록
    public void saveItem(Item item, MultipartFile imgFile) throws Exception {
        //기본 이미지파일 이름 조회
        String oriImgName = imgFile.getOriginalFilename();

        String imgName = "";

        //user.dir : 현재 디렉토리
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";


      //  String projectPath =
        //랜덤한 id 값 생성
        UUID uuid = UUID.randomUUID();
        //랜덤한 id를 통해 파일 이름을 새로 만든다.
        String savedFileName= uuid + "_" + oriImgName; // 파일명 -> imgName

        imgName = savedFileName;

        File saveFile = new File(projectPath, imgName);
        imgFile.transferTo(saveFile);
        //바뀐 파일명을 아이템에 적용한다.
        item.setImgName(imgName);
        //이미지파일 경로
        item.setImgPath("/files/"+imgName);

        itemRepository.save(item);
    }

    //상품 리스트 불러오기
    public List<Item> allItemView() {
        return itemRepository.findAll();
    }

    //상품 하나씩 불러오기
    public Item itemDetail(Long id){
        return itemRepository.findById(id).get();
    }

    //상품 리스트 페이지용 불러오기
    public Page<Item> allItemViewPage(Pageable pageable){
        return itemRepository.findAll(pageable);
    }

    //상품 검색
    public Page<Item> itemSearchList(String searchKeyword, Pageable pageable){
        return itemRepository.findByItemNameContaining(searchKeyword, pageable);
    }

    //상품 수정
    @Transactional
    public void itemModify(Item item, Long id, MultipartFile imgFile) throws Exception{
        String projectPath = System.getProperty("user.dir")+"/src/main/resources/static/files/";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid+"_"+imgFile.getOriginalFilename();
        File saveFile = new File(projectPath,fileName);
        imgFile.transferTo(saveFile);

        //업데이트할 상품을 id로 조회
        Item update = itemRepository.findItemById(id);
        update.setItemName(item.getItemName());
        update.setItemInfo(item.getItemInfo());
        update.setPrice(item.getPrice());
        update.setStockQuantity(item.getStockQuantity());
        update.setItemSoldout(item.getItemSoldout());
        update.setImgName(item.getImgName());
        update.setImgPath("/files/"+fileName);
        itemRepository.save(update);
    }


    //상품 삭제

    @Transactional
    public void itemDelete(Long id){
        List<BasketItem> items = basketService.findBasketItemByItemId(id);

        for(BasketItem item : items){
            basketService.basketItemDelete(item.getId());
        }

        itemRepository.deleteById(id);
    }

}
