package com.mng.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.mng.bean.UserRemoveBody;
import com.mng.controller.account.AccountControllerBase;
import com.mng.util.JsonBuilder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

public class AdminRemoveUserController extends AccountControllerBase {
    @RequestMapping(value = "/admin/remove-user", method = RequestMethod.POST)
    public JSONObject resolve(HttpServletRequest request, @ModelAttribute("user") UserRemoveBody body) {
        Integer userId = body.getUserId();
        String username = body.getUsername();
        if (userId != null) {
            userRepository.deleteById(userId);
            return JsonBuilder.newObject()
                    .put("success", true)
                    .buildAsJsonObject();
        }
        if (username != null) {
            userRepository.deleteAll(userRepository.findByUsername(username));
            return JsonBuilder.newObject()
                    .put("success", true)
                    .buildAsJsonObject();

        }
        return JsonBuilder.newObject()
                .put("success", false)
                .buildAsJsonObject();
    }
}
