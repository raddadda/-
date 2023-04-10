package com.shop.chan.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.Query;

@Getter
@Setter
public class MainItemDto {
    private Long id;
    private String itemName;
    private String itemInfo;
    private String imgPath;
    private Integer price;

    public MainItemDto(Long id, String itemName, String itemInfo,
                       String imgPath, Integer price){
        this.id=id;
        this.itemName = itemName;
        this.itemInfo = itemInfo;
        this.imgPath = imgPath;
        this.price = price;
    }
}
