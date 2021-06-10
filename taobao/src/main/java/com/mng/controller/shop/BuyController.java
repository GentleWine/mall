package com.mng.controller.shop;

import com.alibaba.fastjson.JSONObject;
import com.mng.annotation.LoginRequired;
import com.mng.entity.Commodity;
import com.mng.entity.Order;
import com.mng.entity.User;
import com.mng.repository.CommodityRepository;
import com.mng.repository.OrderRepository;
import com.mng.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@LoginRequired
public class BuyController {
    static class Log{
        String state;
        String errorinfo;
    }
    @Autowired
    protected OrderRepository orderRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CommodityRepository commodityRepository;

    @RequestMapping(value = "/shopping_cart/buy",method = RequestMethod.POST)
    public Log a(HttpServletRequest request,@RequestParam("data")String data){
            //@RequestParam("address") String address,@RequestParam("data")String data){


        String[] temp1=data.split("}");
        for(int j =0;j<temp1.length-1;j++){
            String[] temp=temp1[j].split(",");
            System.out.println(temp.length);
            for (String i :temp){
                System.out.println(i);
            }
            String id="";
            String payment="";
            if (j==0){
                id=temp[0].substring(7);
                payment=temp[1].substring(13);
            }
            else {
                id=temp[1].substring(6);
                payment=temp[2].substring(13);
            }
            System.out.println(id);
            int comid=Integer.parseInt(id.substring(1,id.length()-1));
            int price=Integer.parseInt(payment);
            System.out.println(comid);
            Order order = new Order();
            order.setOrderid(0);
            order.setStatus(1);
            order.setPaymenttime(new Date());
            order.setPaymenttype(1);
            String username = (String) request.getSession().getAttribute("username");
            List<User> users = userRepository.findByUsername(username);
            User user = users.get(0);
            order.setUserid(user.getUserid());


            order.setComid(comid);
            List<Commodity> shops=commodityRepository.findByComid(comid);
            order.setShopid(shops.get(0).getShopid());
            order.setPayment((double) price);
            order.setNumber((int) (price / shops.get(0).getPrice()));
            buy(order, user);
        }


        //buy(order, user);

        Log log= new Log();
        log.state="SUCCESS";
        log.errorinfo="None";
        return log;
    }



    //生成订单方法,要求传入订单i和user，然后保存，然后购买的用户增加spentmoney
    public void buy(Order i, User user) {
        Double temp = user.getSpentmoney();
        temp += i.getPayment();
        user.setSpentmoney(temp);
        List<Commodity> items = commodityRepository.findByComid(i.getComid());
        items.get(0).setAmount(items.get(0).getAmount()-i.getNumber());
        userRepository.save(user);
        orderRepository.save(i);
        commodityRepository.save(items.get(0));
    }

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
