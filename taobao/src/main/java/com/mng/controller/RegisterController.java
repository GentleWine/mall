package com.mng.controller;

import com.mng.repository.UserRepository;
import com.mng.bean.RegisterBody;
import com.mng.data.UserType;
import com.mng.entity.EntityUser;
import com.mng.exception.AuthenticationException;
import com.mng.exception.RegisterFailedException;
import com.mng.util.JsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    @Autowired
    UserRepository userRepository;
    EntityUser user;
    String phone;
    String password;
    String confirm;
    int usertype;
    String username;
    String mail;

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("register") RegisterBody requestBody) {
        try {
            phone = requestBody.getPhone();
            password = requestBody.getPassword();
            confirm = requestBody.getConfirm();
            try {
                usertype = Integer.parseInt(requestBody.getUsertype());
            } catch (IllegalArgumentException e) {
                throw new RegisterFailedException("Invalid User Type: " + requestBody.getUsertype());
            }
            username = requestBody.getUsername();
            mail = requestBody.getMail();
            if (phone.isEmpty() || password.isEmpty() || confirm.isEmpty() || username.isEmpty() || mail.isEmpty()) {
                throw new RegisterFailedException("At least one of the required fields is empty!");
            } else if (!password.equals(confirm)) {
                throw new RegisterFailedException("Your password and confirmation password do not match.");
            } else if (!userRepository.findByPhone(phone).isEmpty()) {
                throw new RegisterFailedException("A user with that phone number has already existed.");
            } else if (!UserType.isValidUserType(usertype)) {
                throw new RegisterFailedException("User type is invalid.");
            } else {
                user = new EntityUser();
                user.setPhone(phone);
                user.setPassword(password);
                user.setUsertype(usertype);
                user.setUsername(username);
                user.setMail(mail);
                userRepository.save(user);
                return JsonBuilder.newObject()
                        .put("status", "success")
                        .put("username", username)
                        .put("phone", phone)
                        .put("password", password)
                        .put("usertype", String.valueOf(usertype))
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
