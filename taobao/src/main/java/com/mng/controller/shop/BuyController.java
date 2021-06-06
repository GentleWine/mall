package com.mng.controller.shop;

import com.mng.annotation.LoginRequired;
import com.mng.entity.Orders;
import com.mng.entity.User;
import com.mng.repository.OrderRepository;
import com.mng.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@LoginRequired
public class BuyController {
    @Autowired
    protected OrderRepository orderRepository;

    @Autowired
    protected UserRepository userRepository;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String a(Model model, HttpServletRequest request){
        List<Orders> orders=getAllOrders();
        System.out.println(orders.toString());
        model.addAttribute("orders",orders);
        return "a";
    }

    @RequestMapping(value = "/test-buy", method = RequestMethod.POST)
    public String test(Model model, HttpServletRequest request,@RequestParam(value="comid")Integer comid,
                       @RequestParam(value="shopid")Integer shopid,@RequestParam(value="number")Integer number,
                       @RequestParam(value="pay")String  pay) {
        Orders order=new Orders();
        //set time的时候用 new Date() 类似于这样从前端导入数据
        /*
        Orders i = new Orders();
        i.setNumber(1);
        i.setPayment(123.1);
        i.setPaymenttype(1);
        i.setStatus(1);
        i.setUserid(39);
        i.setPaymenttime(new Date());
        buy(i);*/
        order.setOrderid(0);
        order.setStatus(1);
        order.setPaymenttime(new Date());
        order.setPaymenttype(1);
        String username= (String) request.getSession().getAttribute("username");
        List<User> users=userRepository.findByUsername(username);
        User user=users.get(0);
        order.setUserid(user.getUserid());

        order.setNumber(number);
        order.setComid(comid);
        order.setShopid(shopid);
        order.setPayment(Double.parseDouble(pay));
        System.out.println(order.toString());
        try{buy(order,user);} catch (Exception e) {
            e.printStackTrace();
        }

        return "a";
    }

    //生成订单方法,要求传入订单i和user，然后保存，然后购买的用户增加spentmoney
    public void buy(Orders i,User user){
        Double temp=user.getSpentmoney();
        temp+=i.getPayment();
        user.setSpentmoney(temp);
        userRepository.save(user);
        orderRepository.save(i);
    }

    //从数据库获取所有的订单数据，返回一个List
    public List<Orders> getAllOrders(){
        List<Orders> orders=orderRepository.findAll();
        return orders;
    }

    //管理员用的 获取该商品的所有订单
    public List<Orders> getOrdersByComid(int comid){
        List<Orders> orders=orderRepository.findByComid(comid);
        return orders;
    }

    //顾客用，获取顾客名下所有订单
    public List<Orders> getOrdersByUserid(int userid){
        List<Orders> orders=orderRepository.findByUserid(userid);
        return orders;
    }
    //seller用，获取seller名下所有订单
    public List<Orders> getOrdersByShopid(int shopid){
        List<Orders> orders=orderRepository.findByShopid(shopid);
        return orders;
    }
}