package com.mng.controller.admin;

import com.mng.bean.*;
import com.mng.data.UserType;
import com.mng.entity.User;
import com.mng.exception.authentication.LoginFailedException;
import com.mng.exception.authentication.LoginFailedException.Status;
import com.mng.util.JsonBuilder;
import com.mng.util.VerificationUtil;
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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, @ModelAttribute("login") AdminLoginBody body) {
        final String phone = body.getPhone();
        final String password = body.getPassword();

        try {
            if (VerificationUtil.anyIsEmpty(phone, password)) {
                throw new LoginFailedException(Status.FIELD_MISSING);
            }
            List<User> users = userRepository.findByPhone(phone);
            if (users.isEmpty()) {
                throw new LoginFailedException(Status.ACCOUNT_NOT_FOUND);
            }
            User user = users.get(0);
            if (!user.getPassword().equals(password)) {
                throw new LoginFailedException(Status.PASSWORD_INCORRECT);
            }
            if (UserType.ADMIN != user.getEnumUserTypeOrNull()) {
                throw new LoginFailedException(Status.NOT_ADMIN);
            }
            request.getSession().setAttribute("admin_username", user.getUsername());
            return JsonBuilder.newObject()
                    .put("success", true)
                    .build();
        } catch (LoginFailedException e) {
            return JsonBuilder.newObject()
                    .put("success", false)
                    .put("error", e.getMessage())
                    .build();
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String generateUserTable(HttpServletRequest request, UserTableRequestBody body) {
        setLimit(body.getLimit());
        setPage(body.getPage());
        String json = generateUserListJson();
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
