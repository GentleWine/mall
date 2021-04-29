package com.mng.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.mng.bean.AdminLoginBody;
import com.mng.bean.UserRemoveBody;
import com.mng.controller.account.AccountControllerBase;
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
public class AdminController extends AccountControllerBase {

    // TODO: Move admin validation to SQL query
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "123456";

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONObject login(HttpServletRequest request, @ModelAttribute("login") AdminLoginBody body) {
        if (body.getUsername().equals(ADMIN_USERNAME) && body.getPassword().equals(ADMIN_PASSWORD)) {
            return JsonBuilder.newObject()
                    .put("success", true)
                    .buildAsJsonObject();
        }
        return JsonBuilder.newObject()
                .put("success", false)
                .buildAsJsonObject();
    }

    @RequestMapping(value = "/remove-user", method = RequestMethod.POST)
    public JSONObject removeUser(HttpServletRequest request, @ModelAttribute("user") UserRemoveBody body) {
        final Integer userId = body.getUserId();
        final String username = body.getUsername();
        final JSONObject jsonSuccess = JsonBuilder.newObject()
                .put("success", true)
                .buildAsJsonObject();
        final JSONObject jsonFail = JsonBuilder.newObject()
                .put("success", false)
                .buildAsJsonObject();
        if (userId != null) {
            Optional<User> userToRemove = userRepository.findById(userId);
            if (userToRemove.isPresent()) {
                userRepository.delete(userToRemove.get());
                return jsonSuccess;
            }
        }
        if (username != null) {
            List<User> usersToRemove = userRepository.findByUsername(username);
            if (!usersToRemove.isEmpty()) {
                userRepository.deleteAll(usersToRemove);
                return jsonSuccess;
            }
        }
        return jsonFail;
    }
}
