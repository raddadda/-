package com.shop.chan.repository;


import com.shop.chan.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{
    Page<Item> findByItemNameContaining(String searchKeyword, Pageable pageable);
   //find+엔티티이름+By+변수이름
    Item findItemById(Long id);

}
