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
    String phone;
    String password;
    String confirm;
    String usertype;
    String username;
    String mail;
    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    public JSONObject register(@ModelAttribute("register") Registerbody requestbody){
        phone=requestbody.getPhone();
        password=requestbody.getPassword();
        confirm=requestbody.getConfirm();
        usertype=requestbody.getUsertype();
        username=requestbody.getUsername();
        mail=requestbody.getMail();
        try {
            if (phone.isEmpty() || password.isEmpty() || confirm.isEmpty() ||
                    usertype.isEmpty() || username.isEmpty() || mail.isEmpty()) {
                throw new Exception("Please inspect the integrity!");
            }
            else if(!password.equals(confirm)){
                throw new Exception("Inconsistent passwords!");
            }
            else if(!userRepository.findByPhone(phone).isEmpty()){
                throw new Exception("phone has already existed!");
            }
            else if(!(usertype.equals("0") || usertype.equals("1"))){
                throw new Exception("wrong usertype!") ;
            }
            else{
                users = new User();
                users.setPhone(phone);
                users.setPassword(password);
                users.setUsertype(usertype);
                users.setUsername(username);
                users.setMail(mail);
                userRepository.save(users);
                JSONObject json = new JSONObject();
                json.put("status","success");
                json.put("username",username);
                json.put("phone",phone);
                json.put("password",password);
                json.put("usertype",usertype);
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
