package com.mng.Controller;

import com.alibaba.fastjson.JSONObject;
import com.mng.Repository.UserRepository;
import com.mng.bean.Registerbody;
import com.mng.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Register {
    @Autowired
    UserRepository userRepository;
    User users;

    String username;
    String account;
    String password;
    String confirm;
    String usertype;
    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    public JSONObject register(@ModelAttribute("register") Registerbody requestbody){

        username=requestbody.getUsername();
        account=requestbody.getAccount();
        password=requestbody.getPassword();
        confirm=requestbody.getConfirm();
        usertype=requestbody.getUsertype();
        try {
            if (username.isEmpty() || account.isEmpty() || password.isEmpty() || confirm.isEmpty() || usertype.isEmpty()) {
                throw new Exception("Please inspect the integrity!");
            }
            else if(!password.equals(confirm)){
                throw new Exception("Inconsistent passwords!");
            }
            else if(!userRepository.findByAccount(account).isEmpty()){
                throw new Exception("Account has already existed!");
            }
            else if(!(usertype.equals("buyer") || usertype.equals("seller"))){
                throw new Exception("wrong usertype!") ;
            }
            else{
                users = new User();
                users.setUsername(username);
                users.setAccount(account);
                users.setPassword(password);
                users.setUsertype(usertype);
                userRepository.save(users);
                JSONObject json = new JSONObject();
                json.put("status","success");
                json.put("username",username);
                json.put("account",account);
                json.put("password",password);
                json.put("usertype",usertype);
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
