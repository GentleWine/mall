package com.mng.controller.orders;

import com.mng.repository.CommodityRepository;
import com.mng.repository.OrderRepository;
import com.mng.repository.ShopRepository;
import com.mng.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class OrderControllerBase {
    @Autowired
    protected OrderRepository orderRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ShopRepository shopRepository;

    @Autowired
    protected CommodityRepository commodityRepository;
}
