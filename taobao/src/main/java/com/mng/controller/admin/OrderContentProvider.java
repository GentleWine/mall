package com.mng.controller.admin;

import com.mng.bean.response.OrderListResponse;
import com.mng.controller.orders.OrderControllerBase;
import com.mng.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class OrderContentProvider extends OrderControllerBase {
    private int page = 1;
    private int limit = 2;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<Order> findOrdersByPageAndLimit(int page, int limit) {
        Page<Order> pages = orderRepository.findAll(PageRequest.of(page - 1, limit));
        return pages.getContent();
    }

    public OrderListResponse generateOrderList() {
        List<Order> allOrders = findOrdersByPageAndLimit(this.getPage(), this.getLimit());
        return OrderListResponse.builder()
                .code(0)
                .msg("")
                .count(orderRepository.count())
                .data(allOrders)
                .build();
    }
}
