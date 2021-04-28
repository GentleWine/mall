package com.mng.Controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.mng.Controller.account.AccountControllerBase;
import com.mng.bean.UserRemoveBody;
import com.mng.entity.User;
import com.mng.util.JsonBuilder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class AdminRemoveUserController extends AccountControllerBase {
    @RequestMapping(value = "/admin/remove-user", method = RequestMethod.POST)
    public JSONObject resolve(HttpServletRequest request, @ModelAttribute("user") UserRemoveBody body) {
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
