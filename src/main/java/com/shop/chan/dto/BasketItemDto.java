package com.shop.chan.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasketItemDto {
    @NotNull(message="상품 아이디를 입력해주세요.")
    private Long itemId;

    @Min(value = 1, message = "최소 1개 수량을 입력해주세요.")
    private int count;
}
