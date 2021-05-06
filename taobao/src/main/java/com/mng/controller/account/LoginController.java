package com.mng.controller.account;

import com.mng.bean.LoginBody;
import com.mng.entity.User;
import com.mng.exception.authentication.LoginFailedException;
import com.mng.exception.authentication.LoginFailedException.Status;
import com.mng.util.JsonBuilder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class LoginController extends AccountControllerBase {

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, @ModelAttribute("login") LoginBody loginbody) {
        String phone = loginbody.getPhone();
        String password = loginbody.getPassword();
        try {
            List<User> usersList;
            if ("".equals(phone) || "".equals(password)) {
                throw new LoginFailedException(Status.FIELD_MISSING);
            } else if ((usersList = userRepository.findByPhone(phone)).isEmpty()) {
                throw new LoginFailedException(Status.ACCOUNT_NOT_FOUND);
            } else if (usersList.size() > 1) {
                throw new LoginFailedException(Status.UNKNOWN);
            } else if (!usersList.get(0).getPassword().equals(password)) {
                throw new LoginFailedException(Status.PASSWORD_INCORRECT);
            } else {
                String username = usersList.get(0).getUsername();
                String mail = usersList.get(0).getMail();
                String usertype = usersList.get(0).getUsertype();
                request.getSession().setAttribute("phone", phone);
                request.getSession().setAttribute("username", username);
                request.getSession().setAttribute("usertype", usertype);
                request.getSession().setAttribute("mail", mail);
                return JsonBuilder.newObject()
                        .put("status", Status.SUCCESS)
                        .put("usertype", Integer.parseInt(usertype))
                        .build();
            }
        } catch (LoginFailedException e) {
            return JsonBuilder.newObject()
                    .put("status", e.getStatus())
                    .put("error_description", e.getMessage())
                    .build();
        }
    }
}

