package com.mng.repository;

import com.mng.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

    List<Orders> findByOrderid(Integer orderid);

    List<Orders> findByComid(Integer comid);

    List<Orders> findByUserid(Integer userid);



}
