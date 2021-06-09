package com.mng.controller.orders;

import com.mng.entity.Order;
import com.mng.repository.CommodityRepository;
import com.mng.repository.OrderRepository;
import com.mng.repository.ShopRepository;
import com.mng.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class OrderControllerBase {
    @Autowired
    protected OrderRepository orderRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ShopRepository shopRepository;

    @Autowired
    protected CommodityRepository commodityRepository;


    //从数据库获取所有的订单数据，返回一个List
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders;
    }

    //管理员用的 获取该商品的所有订单
    public List<Order> getOrdersByComid(int comid) {
        List<Order> orders = orderRepository.findByComid(comid);
        return orders;
    }

    //顾客用，获取顾客名下所有订单
    public List<Order> getOrdersByUserid(int userid) {
        List<Order> orders = orderRepository.findByUserid(userid);
        return orders;
    }

    //seller用，获取seller名下所有订单
    public List<Order> getOrdersByShopid(int shopid) {
        List<Order> orders = orderRepository.findByShopid(shopid);
        return orders;
    }
}
