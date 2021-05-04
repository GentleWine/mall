package com.mng.controller.account;

import com.alibaba.fastjson.JSONObject;
import com.mng.bean.RegisterBody;
import com.mng.entity.User;
import com.mng.exception.RegisterFailedException;
import com.mng.util.JsonBuilder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController extends AccountControllerBase {
    User users;
    String phone;
    String password;
    String confirm;
    String usertype;
    String username;
    String mail;

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public JSONObject register(@ModelAttribute("register") RegisterBody requestbody) {
        phone = requestbody.getPhone();
        password = requestbody.getPassword();
        confirm = requestbody.getConfirm();
        usertype = requestbody.getUsertype();
        username = requestbody.getUsername();
        mail = requestbody.getMail();
        try {
            if (phone.isEmpty() || password.isEmpty() || confirm.isEmpty() ||
                    usertype.isEmpty() || username.isEmpty() || mail.isEmpty()) {
                throw new Exception("false");
            } else if (!password.equals(confirm)) {
                throw new Exception("false");
            } else if (!userRepository.findByPhone(phone).isEmpty()) {
                throw new Exception("has been registered");
            } else if (!(usertype.equals("0") || usertype.equals("1") || usertype.equals("2"))) {
                throw new Exception("false");
            } else {
                users = new User();
                users.setPhone(phone);
                users.setPassword(password);
                users.setUsertype(usertype);
                users.setUsername(username);
                users.setMail(mail);
                userRepository.save(users);
                return JsonBuilder.newObject()
                        .put("msg", "true")
                        .buildAsJsonObject();
            }
        } catch (Exception e) {
            JSONObject json = new JSONObject();
            json.put("msg", e.getMessage());
            return json;
        }
    }
}
