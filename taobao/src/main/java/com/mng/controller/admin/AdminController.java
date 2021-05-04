package com.mng.controller.admin;

import com.mng.bean.AdminLoginBody;
import com.mng.bean.UserRemoveBody;
import com.mng.bean.UserTableRequestBody;
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

    @RequestMapping(value = "/remove-user", method = RequestMethod.POST)
    public String removeUser(HttpServletRequest request, @ModelAttribute("user") UserRemoveBody body) {
        final Integer userId = body.getUserId();
        final String username = body.getUsername();
        final String jsonSuccess = JsonBuilder.newObject()
                .put("success", true)
                .build();
        final String jsonFail = JsonBuilder.newObject()
                .put("success", false)
                .build();
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
