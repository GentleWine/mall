package com.mng.controller.admin;

import com.mng.annotation.LoginRequired;
import com.mng.annotation.UserTypeOnly;
import com.mng.bean.request.EntityListRequest;
import com.mng.bean.request.OrderAddRequest;
import com.mng.bean.request.OrderEditRequest;
import com.mng.bean.request.OrderRemoveRequest;
import com.mng.bean.response.MessageResponse;
import com.mng.bean.response.OrderListResponse;
import com.mng.bean.response.SimpleResponse;
import com.mng.data.UserType;
import com.mng.entity.Order;
import com.mng.exception.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author TCreopargh
 */
@RestController
@RequestMapping("/admin/orders")
@LoginRequired
@UserTypeOnly(UserType.ADMIN)
public class AdminOrderController extends OrderContentProvider {
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OrderListResponse generateOrderTable(HttpServletRequest request, EntityListRequest body) {
        setLimit(body.getLimit());
        setPage(body.getPage());
        return generateOrderList();
    }

    @ResponseBody
    @RequestMapping(value = "/edit-order", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public SimpleResponse editOrder(HttpServletRequest request, @ModelAttribute("order") OrderEditRequest body) {
        Order order = orderRepository.findById(body.getOrderId()).orElse(null);
        if (order == null) {
            return SimpleResponse.fail("Order Not Found!");
        }
        try {
            buildOrderWithRequest(order, body);
        } catch (ParseException e) {
            return SimpleResponse.fail("Incorrect date format!");
        } catch (EntityNotFoundException e) {
            return SimpleResponse.fail("Entity with the given ID is not found: " + e.getEntityName());
        }
        orderRepository.save(order);
        return SimpleResponse.success();
    }

    private void buildOrderWithRequest(Order order, OrderAddRequest body) throws ParseException, EntityNotFoundException {
        Date paymentTime = new SimpleDateFormat(OrderAddRequest.timeFormat).parse(body.getPaymentTime());
        if (!commodityRepository.existsById(body.getCommodityId())) {
            throw new EntityNotFoundException("Commodity");
        }
        if (!shopRepository.existsById(body.getShopId())) {
            throw new EntityNotFoundException("Shop");
        }
        if (!userRepository.existsById(body.getUserId())) {
            throw new EntityNotFoundException("User");
        }
        order.setComid(body.getCommodityId());
        order.setNumber(body.getNumber());
        order.setPaymenttime(paymentTime);
        order.setPayment(body.getPayment());
        order.setPaymenttype(body.getPaymentType());
        order.setStatus(body.getStatus());
        order.setShopid(body.getShopId());
        order.setUserid(body.getUserId());
    }

    @ResponseBody
    @RequestMapping(value = "/add-order", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public SimpleResponse addOrder(HttpServletRequest request, @ModelAttribute("order") OrderAddRequest body) {

        Order order = new Order();
        try {
            buildOrderWithRequest(order, body);
        } catch (ParseException e) {
            return SimpleResponse.fail("Incorrect date format!");
        } catch (EntityNotFoundException e) {
            return SimpleResponse.fail("Entity with the given ID is not found: " + e.getEntityName());
        }
        orderRepository.saveAndFlush(order);
        return SimpleResponse.success();
    }

    @ResponseBody
    @RequestMapping(value = "/remove-orders", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageResponse removeOrders(HttpServletRequest request, @RequestBody List<Order> orders) {
        if (orders == null) {
            return MessageResponse.fail("Request is null!");
        }
        if (orders.isEmpty()) {
            return MessageResponse.fail("You didn't select any user!");
        }
        int errorCount = 0;
        for (Order order : orders) {
            try {
                orderRepository.deleteById(order.getOrderid());
            } catch (DataAccessException | IllegalArgumentException e) {
                errorCount++;
            }
        }
        if (errorCount == 0) {
            return MessageResponse.success("Successfully deleted " + orders.size() + " orders!");
        } else {
            return MessageResponse.fail("Successfully deleted " + (orders.size() - errorCount) + " orders, " + errorCount + " orders failed to delete.");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/remove-order", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public SimpleResponse removeOrder(HttpServletRequest request, @ModelAttribute("order") OrderRemoveRequest body) {
        final Integer orderId = body.getOrderId();
        Optional<Order> orderToRemove = orderRepository.findById(orderId);
        if (orderToRemove.isPresent()) {
            orderRepository.delete(orderToRemove.get());
            return SimpleResponse.success();
        }
        return SimpleResponse.fail("Requested order not found");
    }
}
