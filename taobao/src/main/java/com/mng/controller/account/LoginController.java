package com.mng.controller.account;

import com.alibaba.fastjson.JSONObject;
import com.mng.bean.LoginBody;
import com.mng.entity.User;
import com.mng.exception.LoginFailedException;
import com.mng.exception.LoginFailedException.Status;
import com.mng.util.JsonBuilder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class LoginController extends AccountControllerBase {
    List<User> usersList;
    String phone;
    String password;
    String usertype;
    String username;
    String mail;

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public JSONObject login(HttpServletRequest request, @ModelAttribute("login") LoginBody loginbody) {
        phone = loginbody.getPhone();
        password = loginbody.getPassword();
        try {
            if ("".equals(phone) || "".equals(password)) {
                throw new LoginFailedException(Status.FIELD_MISSING);
            } else if ((usersList = userRepository.findByPhone(phone)).isEmpty()) {
                throw new LoginFailedException(Status.ACCOUNT_NOT_FOUND);
            } else if (usersList.size() > 1) {
                throw new LoginFailedException(Status.UNKNOWN);
            } else if (!usersList.get(0).getPassword().equals(password)) {
                throw new LoginFailedException(Status.PASSWORD_INCORRECT);
            } else {
                username = usersList.get(0).getUsername();
                mail = usersList.get(0).getMail();
                usertype = usersList.get(0).getUsertype();
                request.getSession().setAttribute("phone", phone);
                request.getSession().setAttribute("username", username);
                request.getSession().setAttribute("usertype", usertype);
                request.getSession().setAttribute("mail", mail);
                return JsonBuilder.newObject()
                        .put("status", Status.SUCCESS)
                        .put("usertype", Integer.parseInt(usertype))
                        .buildAsJsonObject();
            }
        } catch (LoginFailedException e) {
            return JsonBuilder.newObject()
                    .put("status", e.getStatus())
                    .put("error_description", e.getMessage())
                    .buildAsJsonObject();
        }
    }
}

