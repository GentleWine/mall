package com.mng.Repository;

import com.mng.entity.Commodity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommodityRepository extends JpaRepository<Commodity, Integer> {
    List<Commodity> findByShopid(Integer shopid);
    List<Commodity> findByShopidAndName(Integer shopid,String name);
    List<Commodity> findByComid(Integer comid);
    @Transactional
    void deleteByComid(Integer comid);

    @Modifying
    @Transactional
    @Query(value="update commodity  set amount =:newamount where comid =:comid",nativeQuery = true)
    public int updateAmountByComid(@Param("newamount") Double newamount, @Param("comid") Integer comid);

}
