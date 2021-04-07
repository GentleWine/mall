package com.mng.Controller;

import com.mng.entity.User;
import com.alibaba.fastjson.JSONObject;
import com.mng.Repository.UserRepository;
import com.mng.bean.Loginbody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class Login {
    @Autowired
    UserRepository userRepository;
    List<User> usersList;
    String phone;
    String password;
    String usertype;

    String username;
    String mail;
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public JSONObject login(HttpServletRequest request,@ModelAttribute("login") Loginbody loginbody)
    {
        phone  = loginbody.getPhone();
        password = loginbody.getPassword();
        usertype = loginbody.getUsertype();
        try {
            if (phone.isEmpty() || password.isEmpty()|| usertype.isEmpty()) {
                throw new Exception("Please inspect the integrity!");
            }
            else if ((usersList = userRepository.findByPhone(phone)).isEmpty())
            {
                throw new Exception("no that person!");
            }
            else if(usersList.size()>1){
                throw new Exception("Unknown error! Try again");
            }
            else if(!usersList.get(0).getPassword().equals(password)){
                throw new Exception("wrong Password");
            }
            else if (!usersList.get(0).getUsertype().equals(usertype)){
                throw  new Exception("wrong usertype!");
            }
            else
            {   username=usersList.get(0).getUsername();
                mail=usersList.get(0).getMail();
                request.getSession().setAttribute("phone",phone);
                request.getSession().setAttribute("username",username);
                request.getSession().setAttribute("usertype",usertype);
                request.getSession().setAttribute("mail",mail);
                JSONObject json = new JSONObject();
                json.put("status","success");
                json.put("username",username);
                json.put("usertype",usertype);
                json.put("phone",phone);
                json.put("mail",mail);
                return json;
            }
        }
        catch (Exception e)
        {
            JSONObject json = new JSONObject();
            json.put("status","fail");
            json.put("error",e.getMessage());
            return json;
        }
    }
}

