/*
package com.shop.chan;

import com.shop.chan.entity.Item;
import com.shop.chan.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;


    public void createItemTest(){
        Item item  = new Item();
        System.out.println(" Item item  = new Item();");
        item.setItemName("테스트상품");
        item.setItemInfo("아이템설명");
        item.setPrice(10000);
        item.setStockQuantity(10);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());

    }

    public void createItemList(){
        for(int i=0; i<10; i++){
            Item item = new Item();
            item.setItemName("테스트상품"+i);
            item.setPrice(10000+i);
            item.setItemInfo("상품설명"+i);
            item.setStockQuantity(50);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }


    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNameTest(){
        this.createItemTest();
        List<Item> itemList = itemRepository.findByItemName("테스트 상품");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품명 상품정보 테스트")
    public void findByItemNameOrItemInfo(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNameOrItemInfo("테스트상품1","상품설명1");
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("해당가격보다 작은 상품찾기 테스트")
    public void findByPriceLessThan(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThan(10004);
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("내림차순 상품보기 테스트")
    public void findByPriceLessThanOrderByPriceDesc(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10006);
        for(Item item : itemList){
            System.out.println(item.toString());
        }
    }
*/
/*    @Test
    @DisplayName("@Query 상품찾기 테스트")
    public void findByItemInfo(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemInfo("아이템설명");
        for(Item item: itemList) {
            System.out.println(item.toString());
        }
    }*//*

}
*/
