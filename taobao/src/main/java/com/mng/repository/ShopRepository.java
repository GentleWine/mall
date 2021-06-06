package com.mng.repository;

import com.mng.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> {
    List<Shop> findByShopid(Integer shopid);

    List<Shop> findByOwnerid(Integer ownerid);
}
