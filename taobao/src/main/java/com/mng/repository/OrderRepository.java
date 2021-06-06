package com.mng.repository;

import com.mng.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

    List<Orders> findByOrderid(Integer orderid);

    List<Orders> findByComid(Integer comid);

    List<Orders> findByUserid(Integer userid);

    List<Orders> findByShopid(Integer shopid);


}
