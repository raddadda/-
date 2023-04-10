package com.shop.chan.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasketListDto {
    private Long basketItemId;
    private String itemName;
    private int price;
    private int count;
    //private String imgPath;

    public BasketListDto(Long basketItemId, String itemName, int price, int count /*,String imgPath*/){
        this.basketItemId= basketItemId;
        this.itemName = itemName;
        this.count = count;
        this.price= price;
      //  this.imgPath = imgPath;
    }
}
