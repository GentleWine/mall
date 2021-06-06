package com.mng.repository;

import com.mng.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByOrderid(Integer orderid);

    List<Order> findByComid(Integer comid);

    List<Order> findByUserid(Integer userid);

    List<Order> findByShopid(Integer shopid);


}
