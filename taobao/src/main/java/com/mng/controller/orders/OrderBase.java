package com.mng.Controller.orders;

import com.mng.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderBase {
    @Autowired
    OrderRepository orderRepository;
}
