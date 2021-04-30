package com.mng.Repository;

import com.mng.entity.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommodityRepository extends JpaRepository<Commodity, Integer> {
    List<Commodity> findByShopid(Integer shopid);
    List<Commodity> findByShopidAndName(Integer shopid,String name);
}
