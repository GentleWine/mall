package com.mng.repository;

import com.mng.entity.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommodityRepository extends JpaRepository<Commodity, Integer> {
    List<Commodity> findByShopid(Integer shopid);

    List<Commodity> findByShopidAndName(Integer shopid, String name);

    List<Commodity> findByComid(Integer comid);
    
    List<Commodity> findByName(String name);

    @Transactional
    void deleteByComid(Integer comid);

    @Modifying
    @Transactional
    @Query(value = "update commodity set amount =:newamount where comid =:comid", nativeQuery = true)
    int updateAmountByComid(@Param("newamount") Double newamount, @Param("comid") Integer comid);

}
