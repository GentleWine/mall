package com.mng.repository;

import com.mng.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
    List<Shop> findByShopid(Integer shopid);

    List<Shop> findByOwnerid(Integer ownerid);
}
