package com.mng.controller;

import com.mng.repository.UserRepository;
import com.mng.bean.LoginBody;
import com.mng.entity.EntityUser;
import com.mng.exception.AuthenticationException;
import com.mng.exception.LoginFailedException;
import com.mng.util.JsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class LoginController {
    @Autowired
    UserRepository userRepository;
    List<EntityUser> usersList;
    String phone;
    String password;
    int usertype;

    String username;
    String mail;

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, @ModelAttribute("login") LoginBody requestBody) {
        try {
            phone = requestBody.getPhone();
            password = requestBody.getPassword();
            try {
                usertype = Integer.parseInt(requestBody.getUsertype());
            } catch (IllegalArgumentException e) {
                throw new LoginFailedException("Invalid User Type: " + requestBody.getUsertype());
            }
            if (phone.isEmpty() || password.isEmpty()) {
                throw new LoginFailedException("At least one of the required fields is empty!");
            } else if ((usersList = userRepository.findByPhone(phone)).isEmpty()) {
                throw new LoginFailedException("User with that phone number is not found!");
            } else if (usersList.size() > 1) {
                throw new LoginFailedException("Unknown error! Try again");
            } else if (!usersList.get(0).getPassword().equals(password)) {
                throw new LoginFailedException("Password incorrect. Try again.");
            } else if (!usersList.get(0).getUserType().equals(usertype)) {
                throw new LoginFailedException("Wrong user type!");
            } else {
                username = usersList.get(0).getUsername();
                mail = usersList.get(0).getMail();
                request.getSession().setAttribute("phone", phone);
                request.getSession().setAttribute("username", username);
                request.getSession().setAttribute("usertype", usertype);
                request.getSession().setAttribute("mail", mail);
                return JsonBuilder.newObject()
                        .put("status", "success")
                        .put("username", username)
                        .put("usertype", String.valueOf(usertype))
                        .put("phone", phone)
                        .put("mail", mail)
                        .toString();
            }
        } catch (AuthenticationException e) {
            return JsonBuilder.newObject()
                    .put("status", "fail")
                    .put("error", e.getMessage())
                    .toString();
        }
    }
}

