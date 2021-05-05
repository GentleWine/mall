package com.mng.controller.admin;

import com.mng.bean.*;
import com.mng.entity.User;
import com.mng.util.JsonBuilder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController extends UserContentProvider {

    // TODO: Move admin validation to SQL query
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "123456";

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, @ModelAttribute("login") AdminLoginBody body) {
        final String username = body.getUsername();
        final String password = body.getPassword();
        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            request.getSession().setAttribute("admin_username", username);
            return JsonBuilder.newObject()
                    .put("success", true)
                    .build();
        }
        return JsonBuilder.newObject()
                .put("success", false)
                .build();
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String generateUserTable(HttpServletRequest request, UserTableRequestBody body) {
        setLimit(body.getLimit());
        setPage(body.getPage());
        String json = findUsers();
        return json == null ? "{}" : json;
    }

    @RequestMapping(value = "/edit-user", method = RequestMethod.POST)
    public String editUser(HttpServletRequest request, @ModelAttribute("user") UserEditBody body) {
        User user = userRepository.findById(body.getId()).orElse(null);
        if (user == null) {
            return JsonBuilder.newObject()
                    .put("success", false)
                    .put("reason", "User Not Found!")
                    .build();
        }
        user.setMail(body.getMail());
        user.setUsername(body.getUsername());
        user.setUsertype(body.getUsertype());
        user.setPassword(body.getPassword());
        user.setPhone(body.getPhone());
        userRepository.save(user);
        return JsonBuilder.newObject()
                .put("success", true)
                .build();
    }


    // TODO: Fix adding the user twice
    @RequestMapping(value = "/add-user", method = RequestMethod.POST)
    public String addUser(HttpServletRequest request, @ModelAttribute("user") UserAddBody body) {
        User user = new User();
        user.setMail(body.getMail());
        user.setUsername(body.getUsername());
        user.setUsertype(body.getUsertype());
        user.setPassword(body.getPassword());
        user.setPhone(body.getPhone());
        userRepository.saveAndFlush(user);
        return JsonBuilder.newObject()
                .put("success", true)
                .build();
    }

    @RequestMapping(value = "/remove-user", method = RequestMethod.POST)
    public String removeUser(HttpServletRequest request, @ModelAttribute("user") UserRemoveBody body) {
        final Integer userId = body.getUserId();
        final String jsonSuccess = JsonBuilder.newObject()
                .put("success", true)
                .build();
        final String jsonFail = JsonBuilder.newObject()
                .put("success", false)
                .build();
        Optional<User> userToRemove = userRepository.findById(userId);
        if (userToRemove.isPresent()) {
            userRepository.delete(userToRemove.get());
            return jsonSuccess;
        }
        return jsonFail;
    }
}
