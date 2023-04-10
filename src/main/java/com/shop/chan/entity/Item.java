package com.shop.chan.entity;
/*
import com.shop.chan.dto.ItemFormDto;*/
import com.shop.chan.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="item")
public class Item {

    @Id
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String itemName; //상품 이름

    @Column(name="price" , nullable = false)
    private int price;      //가격

    private int stockQuantity; //재고

    private int count; //판매 개수

    private String itemInfo; //아이템 정보

    private int itemSoldout; //상품 상태 (0: 판매중 1: 품절)

    private String imgName; //상품 이미지 파일명

    private String imgPath; // 이미지 조회 경로

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate; // 상품 등록 날짜

    @PrePersist
    public void createDate() {
        this.createDate = LocalDate.now();
    }

/*    public Item(String itemName, String itemInfo, int price,int stockQuantity){
        this.itemName= itemName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.itemInfo = itemInfo;
    }*/
/*    public void updateItem(ItemFormDto itemFormDto) {
        this.itemName = itemFormDto.getItemName();
        this.price = itemFormDto.getPrice();
        this.stockQuantity = itemFormDto.getStockQuantity();
        this.itemInfo = itemFormDto.getItemInfo();
    }*/

/*    public void removeStock(int stock){
        int restStock = this.stockQuantity - stock;

        if(restStock < 0){
            throw new NotEnoughStockException("재고가 부족합니다.");
        }
    }

    public void addStock(int stock) {
        this.stockQuantity += stock;
    }*/
}
