package com.mng.Repository;

import com.mng.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
    List<Shop> findByShopid(Integer shopid);
}
