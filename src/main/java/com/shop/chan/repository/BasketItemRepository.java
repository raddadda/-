package com.shop.chan.repository;

import com.shop.chan.dto.BasketListDto;
import com.shop.chan.entity.Basket;
import com.shop.chan.entity.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BasketItemRepository extends JpaRepository<BasketItem,Long> {

    BasketItem findByBasketIdAndItemId(Long basketId, Long itemId);

    List<BasketItem> findBasketItemByItemId(Long id);

    BasketItem findBasketItemById(Long id);
}
